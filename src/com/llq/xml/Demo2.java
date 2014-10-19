package com.llq.xml;

import static org.junit.Assert.*;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//使用dom方式对xml文档进行crud
public class Demo2 {

	/**
	 * 读取xml文档中，<书名>JavaScript网页开发</书名>  节点中的值
	 * @throws Exception
	 */
	@Test
	public void read1() throws Exception {
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		NodeList noedList=document.getElementsByTagName("书名");
		Node node=noedList.item(1);
		String textContent=node.getTextContent();
		System.out.println(textContent);
		
	}
	
	/**
	 * 得到xml文档中的所有标签
	 * @throws Exception
	 */
	@Test
	public void read2() throws Exception {
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		//得到根节点
		Node root=document.getElementsByTagName("书架").item(0);
		
		list(root);
		
	}

	private void list(Node node) {
		
		if(node instanceof Element){
			System.out.println(node.getNodeName());
		}
		NodeList nodeList=node.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			Node child=nodeList.item(i);
			list(child);
		}
	}
	
	
	/**
	 * 得到xml文档中标签属性的值:<书名  name="xxxxx">Java就业培训教程</书名>
	 * @throws Exception
	 */
	@Test
	public void read3() throws Exception {
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");

		Element bookname=(Element) document.getElementsByTagName("书名").item(0);
		String value=bookname.getAttribute("name");
		System.out.println(value);
		
	}
	
	/**
	 * 向xml文档中添加节点：<售价>59.00元</售价>
	 * @throws Exception
	 */
	@Test
	public void add1() throws Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		//创建节点
		Element price=document.createElement("售价");
		price.setTextContent("59.00元");
		
		//把创建的节点挂到第一本书上
		Element bookname=(Element) document.getElementsByTagName("书").item(0);
		bookname.appendChild(price);
		
		//将更新后内存写回到xml文档
		TransformerFactory tffatory=TransformerFactory.newInstance();
		Transformer tf=tffatory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
		
	}
	
	/**
	 * 向xml文档中指定位置添加节点：<售价>59.00元</售价>
	 * @throws Exception
	 */
	@Test
	public void add2() throws Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		//创建节点
		Element price=document.createElement("售价");
		price.setTextContent("59.00元");
		
		//得到参考节点
		Element refNode=(Element) document.getElementsByTagName("售价").item(0);
		
		//得到要挂崽的节点
		Element book=(Element) document.getElementsByTagName("书").item(0);
		
		//往book的指定位置上插崽
		book.insertBefore(price, refNode);
		
		//将更新后内存写回到xml文档
		TransformerFactory tffatory=TransformerFactory.newInstance();
		Transformer tf=tffatory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
		
	}
	
			
	/**
	 * 向xml文档中添加属性：<书名>Java就业培训教程</书名> 如：变为<书名 name="xxx">Java就业培训教程</书名>
	 * @throws Exception
	 */
	@Test
	public void add3() throws Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		//创建节点
		Element price=document.createElement("售价");
		price.setTextContent("59.00元");
		

		//得到要添加属性的节点
		Element book=(Element) document.getElementsByTagName("书名").item(0);
		book.setAttribute("name", "xxx");
		
		//将更新后内存写回到xml文档
		TransformerFactory tffatory=TransformerFactory.newInstance();
		Transformer tf=tffatory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
		
	}
	
	
	/**
	 * 删除xml文档中 <售价>59.00元</售价>
	 * @throws Exception
	 */
	@Test
	public void delete1() throws Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		//得到要添删除的节点
		Element price=(Element) document.getElementsByTagName("售价").item(0);
		
		//得到要添删除的节点的爸爸
		Element book=(Element) document.getElementsByTagName("书").item(0);
		
		//爸爸再删崽
		book.removeChild(price);
		
		//将更新后内存写回到xml文档
		TransformerFactory tffatory=TransformerFactory.newInstance();
		Transformer tf=tffatory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
		
	}
	
	/**
	 * 删除xml文档中 <售价>59.00元</售价>
	 * @throws Exception
	 */
	@Test
	public void delete2() throws Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		//得到要添删除的节点
		Element price=(Element) document.getElementsByTagName("售价").item(0);
		
		price.getParentNode().removeChild(price);
		
		//将更新后内存写回到xml文档
		TransformerFactory tffatory=TransformerFactory.newInstance();
		Transformer tf=tffatory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
		
	}
	
	/**
	 * 删除整个xml文档
	 * @throws Exception
	 */
	@Test
	public void delete3() throws Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		//得到要添删除的节点
		Element price=(Element) document.getElementsByTagName("售价").item(0);
		
		price.getParentNode().getParentNode().getParentNode().removeChild(price.getParentNode().getParentNode());
		
		//将更新后内存写回到xml文档
		TransformerFactory tffatory=TransformerFactory.newInstance();
		Transformer tf=tffatory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
		
	}
	
	/**
	 * 更新节点：更新售价
	 * @throws Exception
	 */
	@Test
	public void update1() throws Exception {
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder=factory.newDocumentBuilder();
		Document document=documentBuilder.parse("src/book.xml");
		
		//得到要添删除的节点
		Element price=(Element) document.getElementsByTagName("售价").item(0);
		price.setTextContent("109.00元");
		
		//将更新后内存写回到xml文档
		TransformerFactory tffatory=TransformerFactory.newInstance();
		Transformer tf=tffatory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/book.xml")));
	}
	
	
}
