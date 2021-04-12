package com.springboot.pjt1.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.pjt1.repository.dto.BestPrice;
import com.springboot.pjt1.repository.dto.Product;
import com.springboot.pjt1.repository.mapper.BestPriceMapper;
import com.springboot.pjt1.repository.mapper.ProductMapper;

@Service
public class BestPriceServiceImpl implements BestPriceService {
	
	@Autowired
	BestPriceMapper bpmapper;
	
	@Autowired
	ProductMapper proMapper;
	
	@Override
	public List<BestPrice> selectBestPriceByProductNo(int productNo) {
		return bpmapper.selectBestPriceByProductNo(productNo);
	}
	
	@Override
	public boolean deleteBestPriceByProductNo(int productNo) {
		return bpmapper.deleteBestPriceByProductNo(productNo) == 1;
	}
	
	@Override
	public void insertBestPrice(int productNo) throws IOException {
		Product p = proMapper.selectProductByProductNo(productNo);
		List<BestPrice> list = getBestPriceList(p.getProductName());
		for(BestPrice bp : list) {
			bp.setProductNo(productNo);
			if(bp.getPrice().length() > 20) continue;
			bpmapper.insertBestPrice(bp);
		}
	}
	
	@Override
	public List<BestPrice> getBestPriceList(String name) throws IOException {
		name = name.replaceAll("\\(", "");
		name = name.replaceAll("\\)", "");
		for (int i = 0; i < name.length(); i++) {
			if(name.charAt(i) >= 48 && name.charAt(i) <= 57) {
				name = name.substring(0, i);
				break;
			}
		}
		System.out.println(name);
		List<BestPrice> list = new ArrayList<>();
		
		{
		// 쿠팡 성공
			try {
				Document doc = Jsoup.connect("https://www.coupang.com/np/search?component=&q=" + name + "&filterType=rocket").get();
				Elements products = doc.select("ul#productList li");
				for(Element p : products) {
					if(p.className().contains("search-product__ad-badge")) continue;
					BestPrice bp = new BestPrice();
					bp.setSiteName("쿠팡");
					bp.setProductName(p.select("dd.descriptions div.name").text());
					bp.setPrice(p.select("dd.descriptions div.price strong").text());
					bp.setLink("https://www.coupang.com" + p.select("a").attr("href"));
					list.add(bp);
					break;
				}
			} catch(Exception e) {
				System.err.println(e);
			}
		}
		{
			// 지마켓 성공
			try {
				Document doc = Jsoup.connect("https://browse.gmarket.co.kr/search?keyword=" + name).get();
				Element product = doc.select("div#section__inner-content-body-container div.section__module-wrap div.box__component").get(2);
				BestPrice bp = new BestPrice();
				bp.setSiteName("지마켓");
				Elements container = product.select("div.box__item-container div.box__information-major");
				bp.setProductName(container.select("span.text__brand").text() + " " + container.select("span.text__item").attr("title"));
				bp.setPrice(container.select("div.box__price-seller strong.text__value").text());
				bp.setLink(container.select("a").attr("href"));
				list.add(bp);
			} catch(Exception e) {
				System.err.println(e);
			}
		}
		{
			// 인터파크.. 성공!!
			try {
				Document doc = Jsoup.connect("http://isearch.interpark.com/isearch?q=" + name).get();
				Element product = doc.select("ul#_SHOPListLi li.goods div.productResultList").get(0);
				BestPrice bp = new BestPrice();
				bp.setSiteName("인터파크");
				bp.setProductName(product.select("div.info a.name").text());
				bp.setPrice(product.select("div.price span.won strong").text());
				bp.setLink(product.select("div.price span.won a").attr("href"));
				list.add(bp);
			} catch(Exception e) {
				System.err.println(e);
			}
		}
		{
			// 옥션 성공
			try {
				Document doc = Jsoup.connect("http://browse.auction.co.kr/search?keyword=" + name).get();
				Element product = doc.select("div#section--inner_content_body_container div.section--module_wrap div.component div.itemcard div.section--itemcard_info_major").get(0);
				BestPrice bp = new BestPrice();
				bp.setSiteName("옥션");
				bp.setProductName(product.select("div.area--itemcard_title a span.text--title").text());
				bp.setPrice(product.select("div.area--itemcard_price span.price_seller strong.text--price_seller").text());
				bp.setLink(product.select("div.area--itemcard_title a").attr("href"));
				list.add(bp);
			} catch(Exception e) {
				System.err.println(e);
			}
		}
		

/*
		{
			 // 티몬 아 티몬도 동적이다 망했네 ㅜㅜ
			Document doc = Jsoup.connect("https://search.tmon.co.kr/search/?keyword=" + name).get();
			System.out.println(doc.toString());
			Elements products = doc.select("div.deallist_wrap ul.list li.item");
			for(Element p : products) {
				BestPrice bp = new BestPrice();
				bp.setName(p.select("div.deal_info strong.tx").text());
				bp.setPrice(p.select("div.deal_info span.sale i.num").text());
				bp.setLink(p.select("a").attr("href"));
				list.add(bp);
				break;
			}
		}
		{
			// 위메프도 .. 동적....
			Document doc = Jsoup.connect("https://search.wemakeprice.com/search?search_cate=top&keyword=" + name).get();
			System.out.println(doc.toString());
		}
		{
			// 11번가 제발.... 동적이다...
			Document doc = Jsoup.connect("https://search.11st.co.kr/Search.tmall?kwd=" + name).get();
			Elements product = doc.select("section.search_section");
			System.out.println(product.toString());
		}
		{
			// 롯데ON 불가능..
			Document doc = Jsoup.connect("https://www.lotteon.com/search/search/search.ecn?render=search&platform=pc&q=" + name).get();
			Elements product = doc.select("div.srchResultProductArea");
			System.out.println(product.toString());
		}
		{
			// sk 스토아 제발.. 하
			Document doc = Jsoup.connect("http://www.skstoa.com/goods-search/page/search?keywordSearch=" + name).get();
			Elements product = doc.select("div#goodsConts");
			System.out.println(product.toString());
		}
		{
			// gs 프레시몰도...
			Document doc = Jsoup.connect("https://www.gsfresh.com/ss/search_result?keyword=" + name).get();
			Elements product = doc.select("section.wrap-search-result");
			System.out.println(product.toString());
		}
*/		
		
		return list;
	}
	
	
}
