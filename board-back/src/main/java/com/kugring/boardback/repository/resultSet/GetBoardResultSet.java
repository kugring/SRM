package com.kugring.boardback.repository.resultSet;


// boardEntity타입을 대신해 프로필이미지와 데이터타임을 지정하기 위한 대처 인터페이스를 만든거 같다.
public interface GetBoardResultSet {
    Integer getBoardNumber();
    String getTitle();
    String getContent();
    String getWriteDatetime();
    String getWriterEmail();
    String getWriterNinckname();
    String getWriterProfileImage();
}
