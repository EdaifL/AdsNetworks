package com.test.adsnetworks;

import java.util.ArrayList;

public class JsonGuide {

    public static ArrayList <Guide> GuideList = new ArrayList<>();

    public static class Guide{
        private String Title;
        private String content;
        private String Img;
        public Guide(String title, String content, String img) {
            Title = title;
            this.content = content;
            Img = img;
        }


        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return Img;
        }

        public void setImg(String img) {
            Img = img;
        }
    }

}
