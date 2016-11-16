package com.sky.pm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RateDate {
//	"id" : 46,
//			"creditorRightPkgId" : null,
//			"version" : null,
//			"dateString" : "2016-03-16",
//			"insertTime" : 1458098338000,
//			"createUser" : null,
//			"date" : 1458057600000,
//			"deleteFlag" : "0",
//			"sevenRate" : null,
//			"dateStr" : "03-16",
//			"rate" : 6,
//			"updateUser" : null,
//			"updateTime" : null
	private float rate;//利率
	private String date;//毫秒值


	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getDate() {
		return getNowDate(date);
	}

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 获取现在时间
	 *
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDate(String currentTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		return formatter.format(new Date(Long.parseLong(currentTime)));
	}

}
