import numpy as np
import os
import tensorflow as tf
import tensorflow.contrib.slim as slim
import time

from nets import inception
from preprocessing import inception_preprocessing

classification_checkpoint_dir = "\\tensorflow-dataset\\train_inception_v1_mart_FineTune_logs\\all"

image_size = inception.inception_v1.default_image_size


with tf.Graph().as_default():

   start = time.time()

   image_input = tf.read_file("\\tensorflow-dataset\\test images\\017_267.jpg")
   image = tf.image.decode_jpeg(image_input, channels=3)
   processed_image = inception_preprocessing.preprocess_image(image, image_size, image_size, is_training=False)

   processed_images = tf.expand_dims(processed_image, 0)

   with slim.arg_scope(inception.inception_v1_arg_scope()):
      logits, _ = inception.inception_v1(processed_images, num_classes=17, is_training=False)
   probabilities = tf.nn.softmax(logits)

   init_fn = slim.assign_from_checkpoint_fn(os.path.join(classification_checkpoint_dir, 'model.ckpt-500'), slim.get_model_variables('InceptionV1'))

   config = tf.ConfigProto()
   config.gpu_options.allow_growth = True
   with tf.Session(config = config) as sess:
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
   