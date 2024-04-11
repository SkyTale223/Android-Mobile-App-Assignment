package com.example.fit2081assignment1;

public class EventCategory {
        private String categoryID;
        private String categoryName;
        private String categoryEventCount;
        private String categoryActive;

        public EventCategory(String categoryID, String categoryName, String categoryEventCount, String categoryActive) {
                this.categoryID = categoryID;
                this.categoryName = categoryName;
                this.categoryEventCount = categoryEventCount;
                this.categoryActive = categoryActive;
        }


        public String getCategoryID() {
                return categoryID;
        }

        public String getCategoryName() {
                return categoryName;
        }

        public String getCategoryEventCount() {
                return categoryEventCount;
        }

        public String isCategoryActive() {
                return categoryActive;
        }
}
