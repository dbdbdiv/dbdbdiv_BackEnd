package com.dbdb.dbdb.repository;

import com.dbdb.dbdb.dto.BoardDto;
import com.dbdb.dbdb.dto.UserDto;
import com.dbdb.dbdb.table.Board;
import com.dbdb.dbdb.table.BoardLike;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public void insertBoard(Board board) {
        int category_id = board.getCategory_id();
        int user_id = board.getUser_id();
        int init_views = 0;
        String title = board.getTitle();
        String content = board.getContent();
        boolean notice = board.isNotice();
        String file_name = board.getFile_name();
        String url = board.getUrl();
        LocalDateTime create_at = board.getCreated_at();
        LocalDateTime update_at = board.getUpdated_at();

        jdbcTemplate.update("INSERT INTO `board` (`category_id`, `user_id`, `views`, `title`, `content`, `notice`, `file_name`, `url`, `created_at`, `updated_at`) VALUES (?,?,?,?,?,?,?,?,?,?)"
                , category_id, user_id, init_views, title, content, notice, file_name, url, create_at, update_at);
    }

    public List<BoardDto.BoardWithCommentsCount> findTitleAll() {
        var boardCommentMapper = BeanPropertyRowMapper.newInstance(BoardDto.BoardWithCommentsCount.class);

        return jdbcTemplate.query(
                "SELECT B.id, B.category_id, B.user_id, B.views, B.title, B.notice, B.created_at, COUNT(C.id) as comment_count " +
                        "FROM board B LEFT OUTER JOIN comment C " +
                        "ON B.id = C.write_id AND B.category_id = C.category_id " +
                        "GROUP BY B.category_id, B.id"
                , boardCommentMapper
        );
    }

    public List<BoardDto.BoardWithCommentsCount> findTitleByCategoryId(int category_id) {
        var boardCommentMapper = BeanPropertyRowMapper.newInstance(BoardDto.BoardWithCommentsCount.class);

        return jdbcTemplate.query(
                "SELECT B.id, B.category_id, B.user_id, B.views, B.title, B.notice, B.created_at, COUNT(C.id) as comment_count " +
                        "FROM comment C RIGHT OUTER JOIN board B " +
                        "ON C.write_id = B.id AND C.category_id = B.category_id " +
                        "WHERE B.category_id=? " +
                        "GROUP BY B.id"
                , boardCommentMapper, category_id
        );
    }

    public Board findBoardById(int id) {
        var boardMapper = BeanPropertyRowMapper.newInstance(Board.class);

        Board board = jdbcTemplate.queryForObject(
                "SELECT * FROM `board` WHERE id=?",
                boardMapper, id
        );

        return board;
    }

    public void increaseViewCount(int id) {
        jdbcTemplate.update("UPDATE `board` SET views = views + 1 WHERE id = ?", id);
    }

    public void insertBoardLike(BoardLike boardLike) {
        int user_id = boardLike.getUser_id();
        int category_id = boardLike.getCategory_id();
        int liked_id = boardLike.getLiked_id();

        jdbcTemplate.update("INSERT INTO `board_like` (`user_id`, `category_id`, `liked_id`) VALUES (?,?,?)",
                user_id, category_id, liked_id);
    }

    public void deleteBoardLike(BoardLike boardLike) {
        int user_id = boardLike.getUser_id();
        int category_id = boardLike.getCategory_id();
        int liked_id = boardLike.getLiked_id();

        jdbcTemplate.update("DELETE FROM `board_like` WHERE user_id=? AND category_id=? AND liked_id=?",
                user_id, category_id, liked_id);
    }
}
