package org.example;

public enum MainMenu {
    KITZBAOT_VE_HATAVOT("קצבאות והטבות"),
    DEMEI_BITUAH("דמי ביטוח"),
    MAASIKIM("מעסיקים"),
    SHIRUT_ISHI("שירות אישי"),
    ODOT("אודות");

    private final String menuName;

    MainMenu(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }
}