package com.hermes_un_jardin.hermesunjardin.controller;

import com.hermes_un_jardin.hermesunjardin.HermesUnJardin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by songdeming on 2015/5/14.
 */

/**
 * <?xml version="1.0" encoding="utf-8"?>
 * <Idea name="HermesUnJardin" desc="My Idea Pool">
 * <!--
 * Picture store in data file directory of external storage,
 * such as '/storage/sdcard/Android/data/data/com.hermes_un_jardin.hermesunjardin/files/0/a.png'.
 * -->
 * <Detail desc="you view your idea here. picture: 'a.png'" pic="a.png" />
 * </Idea>
 */
public class Idea {

    public static final String TAG = "Idea";

    private static final String XML_FILENAME = "idea.xml";

    // Attribute key
    private static final String XML_IDEA_ROOT = "Idea";
    private static final String XML_IDEA_NAME = "name";
    private static final String XML_IDEA_ICON = "icon";
    private static final String XML_IDEA_DESC = "desc";
    private static final String XML_DETAIL_ROOT = "Detail";
    private static final String XML_DETAIL_PIC = "pic";
    private static final String XML_DETAIL_DESC = "desc";
    // Extra fields
    private static final String DATA_DIR = HermesUnJardin.getApplication().getFilesDir().getAbsolutePath();
    // Xml fields
    private String mName;
    private String mDesc;
    private List<Detail> mDetailList = new ArrayList<Detail>();
    private String mIcon;

    public Idea(String name) {
        mName = name;
    }

    public static Idea readFrom(String name) {
        Idea idea = new Idea(name);
        idea.read();

        return idea;
    }

    public void read() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new File(getIdeaDir(), XML_FILENAME));

            // reset old data.
            reset();

            // Idea
            Element idea = document.getDocumentElement();
            mName = idea.getAttribute(XML_IDEA_NAME);
            mIcon = idea.getAttribute(XML_IDEA_ICON);
            mDesc = idea.getAttribute(XML_IDEA_DESC);

            // Detail
            NodeList detailList = idea.getChildNodes();
            for (int detailsIt = 0; detailsIt < detailList.getLength(); detailsIt++) {
                Node detail = detailList.item(detailsIt);
                if (!XML_DETAIL_ROOT.equalsIgnoreCase(detail.getNodeName())) {
                    continue;
                }

                String detailPic = detail.getAttributes().getNamedItem(XML_DETAIL_PIC).getNodeValue();
                String detailDesc = detail.getAttributes().getNamedItem(XML_DETAIL_DESC).getNodeValue();

                Detail d = new Detail(this);
                d.mPic = detailPic;
                d.mDesc = detailDesc;

                mDetailList.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Idea
            Element ideaElement = document.createElement(XML_IDEA_ROOT);
            ideaElement.setAttribute(XML_IDEA_NAME, mName);
            ideaElement.setAttribute(XML_IDEA_DESC, mDesc);

            // Details
            for (Detail detail : mDetailList) {
                Element detailElement = document.createElement(XML_DETAIL_ROOT);
                detailElement.setAttribute(XML_DETAIL_PIC, detail.mPic);
                detailElement.setAttribute(XML_DETAIL_DESC, detail.mDesc);

                ideaElement.appendChild(detailElement);
            }

            document.appendChild(ideaElement);

            // To file.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            Source source = new DOMSource(document);
            Result result = new StreamResult(new FileWriter(new File(getIdeaDir(), XML_FILENAME)));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        mName = "";
        mIcon = "";
        mDesc = "";
        mDetailList.clear();
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String mIcoPath) {
        this.mIcon = mIcoPath;
    }

    public String getIconPath() {
        return new File(getIdeaDir(), getIcon()).getAbsolutePath();
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    /**
     * @param detail
     * @return detail index.
     */
    public int addDetail(Detail detail) {
        mDetailList.add(detail);
        return mDetailList.size() - 1;
    }

    public boolean removeDetail(int index) {
        if (0 <= index && index < mDetailList.size()) {
            mDetailList.remove(index);
            return true;
        }

        return false;
    }

    public List<Detail> getDetailList() {
        return mDetailList;
    }

    public void setDetailList(List<Detail> mDetailList) {
        this.mDetailList = mDetailList;
    }

    public String getIdeaDir() {
        return new File(DATA_DIR, mName).getAbsolutePath();
    }

    public static class Detail {
        private Idea mIdea;
        private String mPic;
        private String mDesc;

        public Detail(Idea idea) {
            mIdea = idea;
        }

        public String getPicPath() {
            return new File(mIdea.getIdeaDir(), mPic).getAbsolutePath();
        }

        public void setPicPath(String mPicPath) {
            this.mPic = mPicPath;
        }

        public String getDesc() {
            return mDesc;
        }

        public void setDesc(String mDesc) {
            this.mDesc = mDesc;
        }
    }
}
