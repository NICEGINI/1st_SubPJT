import numpy as np
import os
import tensorflow as tf
import tensorflow.contrib.slim as slim
import time
import schedule
import sys
import pymysql.cursors
import base64
from io import BytesIO
from PIL import Image
import cv2

from nets import inception
from preprocessing import inception_preprocessing

classification_checkpoint_dir = "\\tensorflow-dataset\\train_inception_v1_mart_FineTune_logs\\all"

image_size = inception.inception_v1.default_image_size

image_dir = "photo.jpg"

#def give(line):
#	print(line)
#	return line

def write_file(data, filename):
	# Convert binary data to proper format and write it on Hard Disk
	with open(filename, 'wb') as file:
		file.write(data)

def run():

	#start = time.time()

		#start = time.time()
	connection = pymysql.connect(host='i4d109.p.ssafy.io',port=3306,user="ssafy",password="ssafy",db="shopping", cursorclass=pymysql.cursors.DictCursor)

	with connection.cursor() as cursor:
		sql = "SELECT * FROM images WHERE state = 'F' limit 1"
		cursor.execute(sql)
		result = cursor.fetchone()

		if result:
			image = result['image']
			write_file(image, image_dir)
			
			num = result['num']

			if num == 1: #classification
				with tf.Graph().as_default():
					image = tf.image.decode_jpeg(tf.read_file(image_dir), channels=3)
					processed_image = inception_preprocessing.preprocess_image(image, image_size, image_size, is_training=False)

					processed_images = tf.expand_dims(processed_image, 0)

					with slim.arg_scope(inception.inception_v1_arg_scope()):
						logits, _ = inception.inception_v1(processed_images, num_classes=17, is_training=False)

					probabilities = tf.nn.softmax(logits)
					init_fn = slim.assign_from_checkpoint_fn(os.path.join(classification_checkpoint_dir, 'model.ckpt-500'), slim.get_model_variables('InceptionV1'))

					#config = tf.ConfigProto()
					#config.gpu_options.allow_growth = True
					with tf.Session() as sess:
						init_fn(sess)
						np_image, probabilities = sess.run([image, probabilities])
						probabilities = probabilities[0, 0:]
						sorted_inds = [i[0] for i in sorted(enumerate(-probabilities), key=lambda x:x[1])]

					names = os.listdir("\\tensorflow-dataset\\mart\\mart_photos")

					product = names[sorted_inds[0]]	

					predict = ""

					if product == '001.mandu':
						predict = "CJ 비비고왕교자1.12kg"
					elif product == '002.spam':
						predict = "CJ 스팸 클래식 1,200g (200g*6입)"
					elif product == '003.vita':
						predict = "광동 비타500 100ml x 100병"
					elif product == '004.toreta':
						predict = "토레타 500ml"
					elif product == '005.bread':
						predict = "삼립 아침엔 밀크 식빵 330g"
					elif product == '006.strawberryjam':
						predict = "노브랜드스트로베리잼450g"
					elif product == '007.oliveoil':
						predict = "Olitaria 오로엑스트라버진올리브오일500ml"
					elif product == '008.salt':
						predict = "노브랜드 볶음깨소금 250g"
					elif product == '009.apple':
						predict = "당찬사과 4~11입/봉 (1.8kg)"
					elif product == '010.greengrape':
						predict = "고당도 샤인머스켓 청포도 1kg(2-3송이)"
					elif product == '011.rice':
						predict = "20년산 이맛쌀 20kg"
					elif product == '012.mixnuts':
						predict = "믹스넛 점보 프리미엄 1kg"
					elif product == '013.coffeemix':
						predict = "맥심 모카골드 마일드 커피믹스 250입"
					elif product == '014.nescafe':
						predict = "네스카페 돌체구스토 룽고 30캡슐"
					elif product == '015.absolute':
						predict = "매일 앱솔루트 명작 2단계 800G"
					elif product == '016.pizzacheese':
						predict = "소와나무 이태리안피자치즈 800g"
					elif product == '017.lemonwater':
						predict = "데일리C 레몬워터"

					sql_result = "INSERT INTO images_results(member_id,num,result) VALUES (%s,%s,%s)"
					values = (result['member_id'],1,predict)

					cursor.execute(sql_result, values)
					#cursor.commit()

					sql_final = "UPDATE images SET state = 'T' WHERE id = %s"
					id_value = (result['id'])

					cursor.execute(sql_final, id_value)

					connection.commit()

					print("Successfully Task 1 Done!!!!!!")

				
			else: #object detection

				search_product = result['search']
				print(search_product)

				PATH_TO_FROZEN_GRAPH = "\\tensorflow-models\\pretrained_model\\faster_rcnn_resnet101_coco_2018_01_28_200000\\frozen_inference_graph.pb"

				detection_graph = tf.Graph()
				with detection_graph.as_default():
					od_graph_def = tf.GraphDef()
					with tf.gfile.GFile(PATH_TO_FROZEN_GRAPH, 'rb') as f:
						serialized_graph = f.read()
						od_graph_def.ParseFromString(serialized_graph)
						tf.import_graph_def(od_graph_def, name = "")
					
				def run_inference_for_single_image(image, graph):
					with tf.Session(graph = graph) as sess:
				 
						input_tensor = graph.get_tensor_by_name('image_tensor:0')
				        
						target_operation_names = ['num_detections', 'detection_boxes',
				                                  'detection_scores', 'detection_classes', 'detection_masks']
						tensor_dict = {}
						for key in target_operation_names:
							op = None
							try:
								op = graph.get_operation_by_name(key)
							except:
								continue

							tensor = graph.get_tensor_by_name(op.outputs[0].name)
							tensor_dict[key] = tensor

						if 'detection_masks' in tensor_dict:
							detection_boxes = tf.squeeze(tensor_dict['detection_boxes'], [0])

						output_dict = sess.run(tensor_dict, feed_dict = {input_tensor : [image]})

						output_dict['num_detections'] = int(output_dict['num_detections'][0])
						output_dict['detection_classes'] = output_dict['detection_classes'][0].astype(np.uint8)
						output_dict['detection_boxes'] = output_dict['detection_boxes'][0]
						output_dict['detection_scores'] = output_dict['detection_scores'][0]

						return output_dict
				    
				def return_position(img, output_dict, class_info):
				 
					height, width, _ = img.shape
					obj_index = output_dict['detection_scores'] > 0.1
					scores = output_dict['detection_scores'][obj_index]
					boxes = output_dict['detection_boxes'][obj_index]
					classes = output_dict['detection_classes'][obj_index]

					percentage = 0.1
					position = "위치를 찾지 못했습니다"
					
					x_1 = width / 3
					x_2 = (width / 3) * 2
					y_1 = height / 3
					y_2 = (height / 3) * 2

					for box, cls, score in zip(boxes, classes, scores):

						if class_info[cls][0] == 'toreta': 
							if score > percentage:

								x = (int(box[1] * width) + int(box[3] * width)) / 2
								y = (int(box[0] * height) + int(box[2] * height)) / 2

								if x < x_1:
									if y < y_1:
										position = "왼쪽 위에 있어요"
										percentage = score
									elif y >= y_1 and y <= y_2:
										position = "정면 왼쪽에 있어요"
										percentage = score
									elif y > y_2:
										position = "왼쪽 아래에 있어요"
										percentage = score
								elif x >= x_1 and x <= x_2:
									if y < y_1:
										position = "정면 위에 있어요"
										percentage = score
									elif y >= y_1 and y <= y_2:
										position = "정면에 있어요"
										percentage = score
									elif y > y_2:
										position = "정면 아래에 있어요"
										percentage = score
								elif x > x_2:
									if y < y_1:
										position = "오른쪽 위에 있어요"
										percentage = score
									elif y >= y_1 and y <= y_2:
										position = "정면 오른쪽에 있어요"
										percentage = score
									elif y > y_2:
										position = "오른쪽 아래에 있어요"
										percentage = score

					return position

				class_info = {}
				f = open('\\tensorflow-models\\class_info_2.txt', 'r')
				for line in f:
					info = line.split(', ')
				 
					class_index = int(info[0])
					class_name = info[1]
					color = (int(info[2][1:]), int(info[3]), int(info[4].strip()[:-1]))    
				    
					class_info[class_index] = [class_name, color]
				f.close()
				
				img = cv2.imread(image_dir)
				output_dict = run_inference_for_single_image(img, detection_graph)
				
				position = return_position(img, output_dict, class_info)

				print(position)

				sql_result = "INSERT INTO images_results(member_id,num,result) VALUES (%s,%s,%s)"
				values = (result['member_id'],2,position)

				cursor.execute(sql_result, values)
					
				sql_final = "UPDATE images SET state = 'T' WHERE id = %s"
				id_value = (result['id'])

				cursor.execute(sql_final, id_value)

				connection.commit()

				print("Successfully Task 2 Done!!!!!!")

		else:
			print("no Task!!!")		

	connection.close()


schedule.every(1).seconds.do(run)
#start = time.time()
#run()
#print(time.time() - start)
while True:
	schedule.run_pending()
	time.sleep(1)
	#run()


'''
	image_input = tf.read_file("\\tensorflow-dataset\\test images\\006_032.jpg")
	image = tf.image.decode_jpeg(image_input, channels=3)
	processed_image = inception_preprocessing.preprocess_image(image, image_size, image_size, is_training=False)

	processed_images = tf.expand_dims(processed_image, 0)

	with slim.arg_scope(inception.inception_v1_arg_scope()):
		logits, _ = inception.inception_v1(processed_images, num_classes=16, is_training=False)
	probabilities = tf.nn.softmax(logits)

	init_fn = slim.assign_from_checkpoint_fn(os.path.join(classification_checkpoint_dir, 'model.ckpt-500'), slim.get_model_variables('InceptionV1'))

	with tf.Session() as sess:
		init_fn(sess)
		np_image, probabilities = sess.run([image, probabilities])
		probabilities = probabilities[0, 0:]
		sorted_inds = [i[0] for i in sorted(enumerate(-probabilities), key=lambda x:x[1])]

	#plt.figure()
	#plt.imshow(np_image.astype(np.uint8))
	#plt.axis('off')
	#plt.show()

	names = os.listdir("\\tensorflow-dataset\\mart\\mart_photos")
	#line = ""
	
	for i in range(3):
		index = sorted_inds[i]
		line = 'Probability %0.2f%% => [%s]' % (probabilities[index], names[index])
		print(line)

	#give(line)

	print(time.time() - start)
'''