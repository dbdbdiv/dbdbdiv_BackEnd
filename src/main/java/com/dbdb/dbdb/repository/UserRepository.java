package com.dbdb.dbdb.repository;

import com.dbdb.dbdb.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean insertUser(UserDto userDto) {
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

    public Boolean validateUser(UserDto userdto) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ? AND password = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userdto.getEmail(), userdto.getPassword());
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            // 이메일과 비밀번호가 일치하는 레코드가 없으면 false 반환
            return false;
        }
    }

    public Integer findUserIdByEmail(String email) {
        String sql = "SELECT id FROM user WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            // 이메일에 해당하는 user_id가 없으면 null 반환
            return null;
        }
    }

    public UserDto.UserNameTypeDto findNameTypeNameById ( int user_id){
        var userMapper = BeanPropertyRowMapper.newInstance(UserDto.UserNameTypeDto.class);
        UserDto.UserNameTypeDto userNameTypeDto = jdbcTemplate.queryForObject(
                "SELECT username, user_type FROM `user` WHERE id=?",
                userMapper, user_id
        );

        return userNameTypeDto;
    }

    public void updatePassword(String email, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        jdbcTemplate.update(sql, newPassword, email);
    }

    public void deleteUserById(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
            jdbcTemplate.update(sql, id);
    }

    public List<UserDto> returnAllUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(UserDto.class)
        );
    }

    public void modifyUser(UserDto userDto) {
        String sql = "UPDATE user SET email = ?, username = ?, password = ?, phone_number = ?, weight = ?, age = ?, total_money = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                userDto.getEmail(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getPhone_number(),
                userDto.getWeight(),
                userDto.getAge(),
                userDto.getTotal_money(),
                userDto.getId());
    }

    public UserDto findUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserRowMapper());
    }

    // RowMapper 구현
    private static class UserRowMapper implements RowMapper<UserDto> {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDto user = new UserDto();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setUser_type(rs.getInt("user_type"));
            user.setEmail(rs.getString("email"));
            user.setPhone_number(rs.getString("phone_number"));
            user.setWeight(rs.getInt("weight"));
            user.setAge(rs.getInt("age"));
            user.setTotal_money(rs.getInt("total_money"));
            return user;
        }
    }
}
