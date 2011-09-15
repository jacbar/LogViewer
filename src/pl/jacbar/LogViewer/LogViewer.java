package pl.jacbar.LogViewer;


import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;



import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class LogViewer extends JFrame {


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogViewer frame = new LogViewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	
	private JSplitPane contentPane = null;
	Document dom = null;
	
	
	public LogViewer(){
		setTitle("LogViewer");
		contentPane = new JSplitPane();
		contentPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		contentPane.setDividerSize(0);
		contentPane.setBorder(null);
		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
				File config = new File("config.xml");
				if(config.exists()){
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					try {
						DocumentBuilder db = dbf.newDocumentBuilder();
						dom = db.parse(config);
					} catch (ParserConfigurationException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					} catch (SAXException e3) {
						e3.printStackTrace();
					}
					
					Element root = dom.getDocumentElement();
					Element windowSize =(Element)root.getElementsByTagName("windowSize").item(0);
					setSize(getInt(windowSize, "width"), getInt(windowSize, "height"));
					
					NodeList n = root.getChildNodes();
					Element rootComponent = (Element)n.item(1);
					loadSettings(contentPane, rootComponent);
				
				}
				else{
					contentPane.setLeftComponent(new ChoosePanel());
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					setSize(dim);
				}
			}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				try {
					DocumentBuilder db = dbf.newDocumentBuilder();
					dom = db.newDocument();
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				}
				Element root = dom.createElement("configuration");
				dom.appendChild(root);
				
				
				
				Element size = dom.createElement("windowSize");
				Element width = dom.createElement("width");
				width.appendChild(dom.createTextNode(Integer.toString(LogViewer.this.getWidth())));
				size.appendChild(width);
				Element height = dom.createElement("height");
				height.appendChild(dom.createTextNode(Integer.toString(LogViewer.this.getHeight())));
				size.appendChild(height);
				root.appendChild(size);
				
				saveSettings(contentPane, root);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
						  Transformer transformer;
						try {
							transformer = transformerFactory.newTransformer();
							DOMSource source = new DOMSource(dom);
							StreamResult result =  new StreamResult(new File("config.xml"));
							transformer.transform(source, result);
							
							
						} catch (TransformerException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
						saveSettings(contentPane, root);
						System.exit(0);
			}
			public void windowClosed(WindowEvent e) {
				
			}
			public void windowActivated(WindowEvent e) {}
		});
		
		add(contentPane);

	}
	
	public void saveSettings(JSplitPane pane, Element parentElement){
		Component[] componentTab = {pane.getLeftComponent(), pane.getRightComponent()};
		for(int i=0; i<2; i++){
			if(componentTab[i] != null){
				Element childElement = dom.createElement("component");
				parentElement.appendChild(childElement);
				
				Element side = dom.createElement("side");
				side.appendChild(dom.createTextNode(Integer.toString(i)));
				childElement.appendChild(side);
				
				if(componentTab[i] instanceof JSplitPane){
					
					Element type = dom.createElement("type");
					type.appendChild(dom.createTextNode("Split"));
					childElement.appendChild(type);
					
					JSplitPane p = (JSplitPane)componentTab[i];
					
					Element divider = dom.createElement("divider");
					divider.appendChild(dom.createTextNode(Integer.toString(p.getDividerLocation())));
					childElement.appendChild(divider);
					
					Element splitType = dom.createElement("splitType");
					splitType.appendChild(dom.createTextNode(Integer.toString(p.getOrientation())));
					childElement.appendChild(splitType);
					
					
					saveSettings(p, childElement);
				
				}else if(componentTab[i] instanceof ColorPanePanel){
					
					Element type = dom.createElement("type");
					type.appendChild(dom.createTextNode("Text"));
					childElement.appendChild(type);
					
					ColorPanePanel p = (ColorPanePanel)componentTab[i];
					
					Element fileName = dom.createElement("fileName");
					fileName.appendChild(dom.createTextNode(p.getFileName()));
					childElement.appendChild(fileName);
					
					Element wrap = dom.createElement("wordWrap");
					wrap.appendChild(dom.createTextNode(p.getWordWrap()?"true":"false"));
					childElement.appendChild(wrap);
					
					Element font = dom.createElement("font");
					childElement.appendChild(font);
					
					Element fontName = dom.createElement("name");
					fontName.appendChild(dom.createTextNode(p.getColorPaneFont().getFontName()));
					font.appendChild(fontName);
					
					Element fontSize = dom.createElement("size");
					fontSize.appendChild(dom.createTextNode(Integer.toString(p.getColorPaneFont().getSize())));
					font.appendChild(fontSize);
					
					Element fontStyle = dom.createElement("style");
					fontStyle.appendChild(dom.createTextNode(Integer.toString(p.getColorPaneFont().getStyle())));
					font.appendChild(fontStyle);
					
				}else if(componentTab[i] instanceof ChoosePanel){
					
					Element type = dom.createElement("type");
					type.appendChild(dom.createTextNode("Choose"));
					childElement.appendChild(type);
				}
			}
		}
		
	}
	
	public void loadSettings(JSplitPane pane, Element parent){
		
		String text = getText(parent, "type");
		int side = getInt(parent,"side");
		
		System.out.println(text+" "+side);
		
		if(text.equals("Split")){
			JSplitPane newPane = new JSplitPane(getInt(parent,"splitType"));
			newPane.setDividerLocation(getInt(parent, "divider"));
			newPane.setDividerSize(4);
			newPane.setBorder(null);
			newPane.setResizeWeight(0.5);
			if(side == 0)
				pane.setLeftComponent(newPane);
			else
				pane.setRightComponent(newPane);
			
			NodeList nl = parent.getChildNodes();
			if(nl != null && nl.getLength()>0){
				for(int i=4; i<6; i++){
						loadSettings(newPane, (Element)nl.item(i));
				}
			}
		} else if (text.equals("Text")) {

		} else {
			if(side == 0)
				pane.setLeftComponent(new ChoosePanel());
			else
				pane.setRightComponent(new ChoosePanel());
		}
		
	}
	
	private String getText(Element e, String name){
		String text ="";
		NodeList nl = e.getElementsByTagName(name);
		if(nl != null && nl.getLength() > 0){
			Element child = (Element)nl.item(0);
			text = child.getFirstChild().getNodeValue();
		}
		return text;
	}
	
	private int getInt(Element e, String name) throws NumberFormatException{
		return Integer.parseInt(getText(e,name));
	}
}
