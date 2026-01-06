package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "MSA 프로젝트에 오신 것을 환영합니다");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("message", "로그인");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("message", "회원 가입");
        return "register";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("message", "상품 목록");
        return "products";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("message", "주문 목록");
        return "orders";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        return "reviews";
    }

    @GetMapping("/reviews/{productId}")
    public String reviewsByProduct(@PathVariable Long productId, Model model) {
        model.addAttribute("message", "리뷰 목록");
        model.addAttribute("productId", productId);
        return "reviews"; // reviews.html
    }
    @GetMapping("/cart")
    public String cart(Model model) {return "cart";}

    @GetMapping("/favorites")
    public String favorites(Model model) {return "favorites";}


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("message", "관리자 페이지");
        System.out.println("관리자 페이지");
        return "admin";
    }

    @GetMapping("/admin/user_list")
    public String adminUserList() {
        return "admin_user_list"; // templates/admin_user_list.html
    }

    @GetMapping("/admin/admin_list")
    public String adminAdminList() {
        return "admin_admin_list"; // templates/admin_admin_list.html
    }

    @GetMapping("/admin/admin_write")
    public String adminWritePage() {
        return "admin_admin_write"; // templates/admin_admin_write.html
    }

    @GetMapping("/admin/company_list")
    public String adminCompanyList() {
        return "admin_company_list";
    }

    @GetMapping("/admin/stats")
    public String adminStats() {
        return "admin_stats";
    }

    @GetMapping("/admin/admin_modify")
    public String adminModifyPage() {
        return "admin_admin_modify"; // templates/admin_admin_modify.html
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("message", "고객 목록");
        return "customers";
    }

    // ============================================
// WebController.java 맨 아래 } 위에 추가하세요
// ============================================

    // 기업 로그인
    @GetMapping("/company/login")
    public String companyLogin() {
        return "company/login";
    }

    // 기업 회원가입
    @GetMapping("/company/register")
    public String companyRegister() {
        return "company/register";
    }

    // 기업 마이페이지
    @GetMapping("/company/mypage")
    public String companyMypage() {
        return "company/mypage";
    }

    // 기업 정보 수정
    @GetMapping("/company/mypage/edit")
    public String companyMypageEdit() {
        return "company/mypage_edit";
    }

    // 기업 비밀번호 변경
    @GetMapping("/company/mypage/password")
    public String companyMypagePassword() {
        return "company/password_change";
    }

} 