package com.springboot.pjt1.image.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.pjt1.repository.dto.Images;
import com.springboot.pjt1.repository.dto.ImagesResults;
import com.springboot.pjt1.repository.mapper.ImageMapper;

@Service
public class ImageServiceImpl implements ImageService {

//  // 사진 저장용 코드
//	private final Path fileLocation;
//
//	@Autowired
//	public ImageService(ImageProperties prop) {
//		// application.properties 에 지정해놓은 폴더경로를 상대경로에서 절대 경로로 변환 후 정규화된 경로(불필요한 부분 제거)를
//		// 가진 path 객체 생성..
//		this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
//
//		try {
//			Files.createDirectories(this.fileLocation);
//		} catch (Exception e) {
//			throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
//		}
//	}
//	public String storeFile(MultipartFile file) {	// 폴더에 사진 저장
//		// 파일 실제 이름에서 역슬래쉬를 슬래쉬로 바꿈
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		
//		try {
//			Path targetLocation = this.fileLocation.resolve(fileName); // 폴더와 파일명 합쳐서 경로 만들기
//			
//			// 파일 받은 것을 경로에 넣는데 같은 이름이 있다면 덮어쓰기한다.
//			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//			
//			return targetLocation.toString();
//		} catch (Exception e) {
//			throw new FileUploadException("[" + fileName + "] 파일 업로드에 실패하였습니다. 다시 시도하십시오.", e);
//		}
//	}

	@Autowired
	ImageMapper mapper;
	
	// 일반인과 시각장애인을 위한 상품 확인!! 번호(mode)로는 1번 3번
	@Override
	public String getProductName(MultipartFile file, String memberId) {
		try {
			// 일단 빨리 저장까지만 
			Images img = new Images();
			img.setMemberId(memberId);
			img.setNum(1);
			img.setState('F');
			img.setImage(file.getBytes());
			mapper.insertImage(img);
			
			return getImageResult(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return "처리오류";
		}
	}

	// 시각 장애인을 위한 상품 위치 찾기!! 번호로는 2번
	@Override
	public String getDirection(MultipartFile file, String itemName, String memberId) {
		try {
			// 일단 빨리 저장까지만 
			Images img = new Images();
			img.setNum(2);
			img.setMemberId(memberId);
			img.setState('F');
			img.setImage(file.getBytes());
			img.setSearch(itemName);
			mapper.insertImageWithItemName(img);
			
			return getImageResult(memberId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "처리오류";
		}
	}
	
	String getImageResult(String memberId) throws InterruptedException {
		ImagesResults imagesResults = new ImagesResults();
		mapper.deleteImagesResultsByMemberId(memberId);
		int time = 0;
		while(true) {
			imagesResults = mapper.selectImageResultsByMemberId(memberId);
			System.out.println(imagesResults);
			if(imagesResults != null) break;
			Thread.sleep(1000);
			System.out.println("결과 기다리는중");
			if(time++ == 60) {
				return "결과 없음";
			}
		}
		System.out.println("-----------------"+imagesResults.getResult()+"-----------------");
		return imagesResults.getResult();
	}
	

}
