package kafka_study.producer.repository;

import kafka_study.producer.model.entity.Board;
import kafka_study.producer.model.enums.UseYn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByBoardNoAndUseYn(long boardNo, UseYn useYn);

    @Query("select b from Board b where b.boardNo = :boardNo and b.useYn = :useYn")
    Board selectByBoardNoAndUseYn(@Param("boardNo") long boardNo, @Param("useYn") UseYn useYn);
}
