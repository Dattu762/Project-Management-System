package com.ProjectManagement.Application.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjectManagement.Application.Model.PlanType;
import com.ProjectManagement.Application.Model.User;
import com.ProjectManagement.Application.Response.PaymentLinkResponse;
import com.ProjectManagement.Application.Service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Value("${razorpay.api.key}")
	private String apiKey;
	
	@Value("${razorpay.api.secret}")
	private String apiSecret;
	
	@Autowired
	UserService uservice;
	
	// We need to create the payment gateway link for payments.
	@GetMapping("/{planType}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(
			@PathVariable PlanType planType,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=uservice.findUserProfileByJwt(jwt);
		int amount=799*100;
		if(planType.equals(PlanType.Annually)) {
			amount=amount*12;
			amount=(int) (amount*0.7); //applying 70% Discount
		}
		
		RazorpayClient razorPay=new RazorpayClient(apiKey, apiSecret); // Need to create this client
		
		JSONObject paymentLinkRequest=new JSONObject(); // Create a JsonObject which contains all the paymentrequest details,
		                                                //where we use this to create a paymink link by passing all the requuired details like price, name etccc
		paymentLinkRequest.put("amount", amount);
		paymentLinkRequest.put("currency", "INR");
		
		JSONObject customer=new JSONObject();
		customer.put("name",user.getFullname());
		customer.put("email", user.getEmail());
		
		paymentLinkRequest.put("customer", customer);
		
		JSONObject notify=new JSONObject(); // USed to notify to the user after the payment.
		notify.put("email", true);
//		notify.put("mobile", true); // To notify to the mobile number
		
		paymentLinkRequest.put("notify", notify);
		
		paymentLinkRequest.put("callback_url", "http://localhost:3000/upgrade_plan/success?planTYpe"+planType);// Page url to return back after 
		                                            // successfull payments.
		
		PaymentLink payment=razorPay.paymentLink.create(paymentLinkRequest);// Creating the payment link by passing all the required details.
		String paymentLinkId=payment.get("id");
		String paymentLinkUrl=payment.get("short_url");
		
		
		PaymentLinkResponse res=new PaymentLinkResponse(); // After creating the link we are passing the details to pass to the frontend.
		res.setPayment_link_id(paymentLinkId);
		res.setPayment_link_url(paymentLinkUrl);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
}
