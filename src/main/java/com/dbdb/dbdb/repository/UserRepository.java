package com.dbdb.dbdb.repository;

import com.dbdb.dbdb.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean insertUser(UserDto userDto){
        String sql = "INSERT INTO user (password, username, user_type, email, phone_number, weight, age) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                userDto.getPassword(),
                userDto.getUsername(),
                1,
                userDto.getEmail(),
                userDto.getPhone_number(),
                userDto.getWeight(),
                userDto.getAge());
        return true;
    }

    public Boolean checkEmailDuplicate(UserDto userDto) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try {
            // queryForObject를 사용하여 결과가 1 이상이면 존재하는 것으로 판단
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userDto.getEmail());
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
