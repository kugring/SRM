package com.kugring.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.boardback.entity.UserEntity;

// JpaRepository를 확장해서 UserResPository 인터페이스를 사용할것이다. 해당 인터페이스는 제너릭을 두개를 받는데 첫번째는 해당 entity이고 두번째는 해당 테이블의 pk의 데이터타입을 기입한다.
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

    boolean existsByEmail(String email); //sql문을 작성해서 해당 데이터가 UserEntity테이블의 데이터로 존재하는지 찾는 코드이다.
    boolean existsByNickname(String nickname);
    boolean existsByTelNumber(String TelNumber);

    // 이메일은 유니크이기 때문에 반드시 한개이상의 데이터를 출력시켜준다.
    UserEntity findByEmail(String email);
}
