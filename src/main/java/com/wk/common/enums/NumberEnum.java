package com.wk.common.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum NumberEnum {

    /**
     * 零
     */
    ZERO("0", Short.valueOf("0"), 0, Long.valueOf("0"), new BigDecimal("0")),
    /**
     * 一
     */
    ONE("1", Short.valueOf("1"), 1, Long.valueOf("1"), new BigDecimal("1")),
    /**
     * 二
     */
    TWO("2", Short.valueOf("2"), 2, Long.valueOf("2"), new BigDecimal("2")),
    /**
     * 三
     */
    THREE("3", Short.valueOf("3"), 3, Long.valueOf("3"), new BigDecimal("3")),
    /**
     * 四
     */
    FOUR("4", Short.valueOf("4"), 4, Long.valueOf("4"), new BigDecimal("4")),
    /**
     * 五
     */
    FIVE("5", Short.valueOf("5"), 5, Long.valueOf("5"), new BigDecimal("5")),
    /**
     * 六
     */
    SIX("6", Short.valueOf("6"), 6, Long.valueOf("6"), new BigDecimal("6")),
    /**
     * 七
     */
    SEVEN("7", Short.valueOf("7"), 7, Long.valueOf("7"), new BigDecimal("7")),
    /**
     * 八
     */
    EIGHT("8", Short.valueOf("8"), 8, Long.valueOf("8"), new BigDecimal("8")),
    /**
     * 九
     */
    NINE("9", Short.valueOf("9"), 9, Long.valueOf("9"), new BigDecimal("9")),
    /**
     * 十
     */
    TEN("10", Short.valueOf("10"), 10, Long.valueOf("10"), new BigDecimal("10")),
    /**
     * 十一
     */
    ELEVEN("11", Short.valueOf("11"), 11, Long.valueOf("11"), new BigDecimal("11")),
    /**
     * 十二
     */
    TWELVE("12", Short.valueOf("12"), 12, Long.valueOf("12"), new BigDecimal("12")),
    /**
     * 十三
     */
    THIRTEEN("13", Short.valueOf("13"), 13, Long.valueOf("13"), new BigDecimal("13")),
    /**
     * 十四
     */
    FOURTEEN("14", Short.valueOf("14"), 14, Long.valueOf("14"), new BigDecimal("14")),
    /**
     * 十五
     */
    FIFTEEN("15", Short.valueOf("15"), 15, Long.valueOf("15"), new BigDecimal("15")),
    /**
     * 百
     **/
    HUNDRED("100", Short.valueOf("100"), 100, Long.valueOf("100"), new BigDecimal("100")),
    /**
     * 千
     **/
    THOUSAND("1000", Short.valueOf("1000"), 1000, Long.valueOf("1000"), new BigDecimal("1000")),
    /**
     * 万
     **/
    TEN_THOUSAND("10000", Short.valueOf("10000"), 10000, Long.valueOf("10000"), new BigDecimal("10000")),
    /**
     * 负一
     */
    NEGATIVE_ONE("-1", Short.valueOf("-1"), -1, Long.valueOf("-1"), new BigDecimal("-1")),
    ;

    private String string;

    private Short shortNumber;

    private Integer intNumber;

    private Long longNumber;

    private BigDecimal bigDecimalNumber;

    NumberEnum(String string, Short shortNumber, Integer intNumber, Long longNumber, BigDecimal bigDecimalNumber) {
        this.string = string;
        this.shortNumber = shortNumber;
        this.intNumber = intNumber;
        this.longNumber = longNumber;
        this.bigDecimalNumber = bigDecimalNumber;
    }
}
