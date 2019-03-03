package com.hpetshop.pojo;

import java.util.Date;

public class HpOrder {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.order_id
	 * @mbggenerated
	 */
	private String orderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.payment
	 * @mbggenerated
	 */
	private String payment;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.payment_type
	 * @mbggenerated
	 */
	private Integer paymentType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.post_fee
	 * @mbggenerated
	 */
	private String postFee;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.status
	 * @mbggenerated
	 */
	private Integer status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.create_time
	 * @mbggenerated
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.update_time
	 * @mbggenerated
	 */
	private Date updateTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.payment_time
	 * @mbggenerated
	 */
	private Date paymentTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.consign_time
	 * @mbggenerated
	 */
	private Date consignTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.end_time
	 * @mbggenerated
	 */
	private Date endTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.close_time
	 * @mbggenerated
	 */
	private Date closeTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.shipping_name
	 * @mbggenerated
	 */
	private String shippingName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.shipping_code
	 * @mbggenerated
	 */
	private String shippingCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.user_id
	 * @mbggenerated
	 */
	private Long userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.buyer_message
	 * @mbggenerated
	 */
	private String buyerMessage;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.buyer_nick
	 * @mbggenerated
	 */
	private String buyerNick;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hp_order.buyer_rate
	 * @mbggenerated
	 */
	private Integer buyerRate;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.order_id
	 * @return  the value of hp_order.order_id
	 * @mbggenerated
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.order_id
	 * @param orderId  the value for hp_order.order_id
	 * @mbggenerated
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.payment
	 * @return  the value of hp_order.payment
	 * @mbggenerated
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.payment
	 * @param payment  the value for hp_order.payment
	 * @mbggenerated
	 */
	public void setPayment(String payment) {
		this.payment = payment == null ? null : payment.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.payment_type
	 * @return  the value of hp_order.payment_type
	 * @mbggenerated
	 */
	public Integer getPaymentType() {
		return paymentType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.payment_type
	 * @param paymentType  the value for hp_order.payment_type
	 * @mbggenerated
	 */
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.post_fee
	 * @return  the value of hp_order.post_fee
	 * @mbggenerated
	 */
	public String getPostFee() {
		return postFee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.post_fee
	 * @param postFee  the value for hp_order.post_fee
	 * @mbggenerated
	 */
	public void setPostFee(String postFee) {
		this.postFee = postFee == null ? null : postFee.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.status
	 * @return  the value of hp_order.status
	 * @mbggenerated
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.status
	 * @param status  the value for hp_order.status
	 * @mbggenerated
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.create_time
	 * @return  the value of hp_order.create_time
	 * @mbggenerated
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.create_time
	 * @param createTime  the value for hp_order.create_time
	 * @mbggenerated
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.update_time
	 * @return  the value of hp_order.update_time
	 * @mbggenerated
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.update_time
	 * @param updateTime  the value for hp_order.update_time
	 * @mbggenerated
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.payment_time
	 * @return  the value of hp_order.payment_time
	 * @mbggenerated
	 */
	public Date getPaymentTime() {
		return paymentTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.payment_time
	 * @param paymentTime  the value for hp_order.payment_time
	 * @mbggenerated
	 */
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.consign_time
	 * @return  the value of hp_order.consign_time
	 * @mbggenerated
	 */
	public Date getConsignTime() {
		return consignTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.consign_time
	 * @param consignTime  the value for hp_order.consign_time
	 * @mbggenerated
	 */
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.end_time
	 * @return  the value of hp_order.end_time
	 * @mbggenerated
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.end_time
	 * @param endTime  the value for hp_order.end_time
	 * @mbggenerated
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.close_time
	 * @return  the value of hp_order.close_time
	 * @mbggenerated
	 */
	public Date getCloseTime() {
		return closeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.close_time
	 * @param closeTime  the value for hp_order.close_time
	 * @mbggenerated
	 */
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.shipping_name
	 * @return  the value of hp_order.shipping_name
	 * @mbggenerated
	 */
	public String getShippingName() {
		return shippingName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.shipping_name
	 * @param shippingName  the value for hp_order.shipping_name
	 * @mbggenerated
	 */
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName == null ? null : shippingName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.shipping_code
	 * @return  the value of hp_order.shipping_code
	 * @mbggenerated
	 */
	public String getShippingCode() {
		return shippingCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.shipping_code
	 * @param shippingCode  the value for hp_order.shipping_code
	 * @mbggenerated
	 */
	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode == null ? null : shippingCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.user_id
	 * @return  the value of hp_order.user_id
	 * @mbggenerated
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.user_id
	 * @param userId  the value for hp_order.user_id
	 * @mbggenerated
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.buyer_message
	 * @return  the value of hp_order.buyer_message
	 * @mbggenerated
	 */
	public String getBuyerMessage() {
		return buyerMessage;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.buyer_message
	 * @param buyerMessage  the value for hp_order.buyer_message
	 * @mbggenerated
	 */
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage == null ? null : buyerMessage.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.buyer_nick
	 * @return  the value of hp_order.buyer_nick
	 * @mbggenerated
	 */
	public String getBuyerNick() {
		return buyerNick;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.buyer_nick
	 * @param buyerNick  the value for hp_order.buyer_nick
	 * @mbggenerated
	 */
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick == null ? null : buyerNick.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hp_order.buyer_rate
	 * @return  the value of hp_order.buyer_rate
	 * @mbggenerated
	 */
	public Integer getBuyerRate() {
		return buyerRate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hp_order.buyer_rate
	 * @param buyerRate  the value for hp_order.buyer_rate
	 * @mbggenerated
	 */
	public void setBuyerRate(Integer buyerRate) {
		this.buyerRate = buyerRate;
	}
}