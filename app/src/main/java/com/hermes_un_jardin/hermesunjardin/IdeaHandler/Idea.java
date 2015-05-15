package com.hermes_un_jardin.hermesunjardin.IdeaHandler;

import com.hermes_un_jardin.hermesunjardin.HermesUnJardin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

    // Attribute key
    private static final String XML_IDEA_ROOT = "Idea";
    private static final String XML_IDEA_NAME = "name";
    private static final String XML_IDEA_DESC = "desc";
    private static final String XML_DETAIL_ROOT = "Detail";
    private static final String XML_DETAIL_PIC = "pic";
    private static final String XML_DETAIL_DESC = "desc";
    private String mName;
    private String mDesc;
    private List<Detail> mDetailList = new ArrayList<Detail>();

    /**
     * @param id the sub-directory of data. Range from [0, `HermesUnJardin.IDEA_DETAIL_COUNT`).
     */
    public void read(int id) {
        if (!checkIdValid(id)) {
            return;
        }

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new File(HermesUnJardin.getApplication().getFilesDir(), String.valueOf(id)));

            // reset old data.
            reset();

            // Idea
            Element idea = document.getDocumentElement();
            String ideaName = idea.getAttribute(XML_IDEA_NAME);
            String ideaDesc = idea.getAttribute(XML_IDEA_DESC);
            mName = ideaName;
            mDesc = ideaDesc;

            // Detail
            NodeList detailList = idea.getChildNodes();
            for (int detailsIt = 0; detailsIt < detailList.getLength(); detailsIt++) {
                Node detail = detailList.item(detailsIt);
                String detailPic = detail.getAttributes().getNamedItem(XML_DETAIL_DESC).getNodeValue();
                String detailDesc = detail.getAttributes().getNamedItem(XML_DETAIL_PIC).getNodeValue();

                Detail d = new Detail();
                d.mPicPath = detailPic;
                d.mDesc = detailDesc;

                mDetailList.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(int id) {
        if (!checkIdValid(id)) {
            return;
        }

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
                detailElement.setAttribute(XML_DETAIL_PIC, detail.mPicPath);
                detailElement.setAttribute(XML_DETAIL_DESC, detail.mDesc);

                ideaElement.appendChild(detailElement);
            }

            document.appendChild(ideaElement);

            //
            document.toString();

            //

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        mName = "";
        mDesc = "";
        mDetailList.clear();
    }

    private boolean checkIdValid(int id) {
        return 0 <= id && id < HermesUnJardin.IDEA_DETAIL_COUNT;
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

    public static class Detail {
        private String mPicPath;
        private String mDesc;

        public String getPicPath() {
            return mPicPath;
        }

        public void setPicPath(String mPicPath) {
            this.mPicPath = mPicPath;
        }

        public String getDesc() {
            return mDesc;
        }

        public void setDesc(String mDesc) {
            this.mDesc = mDesc;
        }
    }


}
