package com.ssafy.api.service;

import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.db.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service
@Transactional(readOnly = true)
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Transactional
	public User createUser() {
		User user = new User();
		user.setName("익명의사용자"); // 유저 이름 생성 로직 나중에 구현
		LocalDate date = LocalDate.now();
		user.setRegDate(date);
		user.setCurrentBookPoint(0L);
		user.setCurrentSley(0L);
		user.setWithdrawn(false);
		userRepository.save(user);
		return user;
	}

	public User getUser() {
		Long userId = Long.parseLong((String)jwtService.get("user").get("id"));
		User user = userRepository.findByUserId(userId);
		return user;
	}
}
