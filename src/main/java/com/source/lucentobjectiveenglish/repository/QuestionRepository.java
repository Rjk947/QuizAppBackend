package com.source.lucentobjectiveenglish.repository;

import com.source.lucentobjectiveenglish.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
