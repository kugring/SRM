import React from 'react'
import './style.css';
import {BoardListItem} from 'types/enum/interface';
import {useNavigate} from 'react-router-dom';
import defaultProfileImage from 'assets/image/default-profile-image.png'

interface Props {
    boardListItem: BoardListItem
}
//           component: Board List Item 컴포넌트
export default function BoardItem({boardListItem} : Props) {

    //          properties
    const {boardNumber, title, content, boardTitleImage} = boardListItem;
    const {favoriteCount, commentCount, viewCount} = boardListItem
    const {writerDatetime, writerNickname, writerProfileImage} = boardListItem;

    //          function: 네비게이트 함수               //
    // const navigator = useNavigate();
    //          function: 네비게이트 함수               //
    const onClickHandler = ()  => {
        // navigator(boardNumber);
    }
    //       render: Board List Item 컴포넌트 렌더링        //
    return (
        <div className='board-list-item' onClick={onClickHandler}>
            <div className='board-list-item-main-box'>
                <div className='board-list-item-top'>
                    <div className='board-list-item-profile-box'>
                        <div
                            className='boar-list-item-profile-image'
                            style={{
                                background: `url(${writerProfileImage ? writerProfileImage : defaultProfileImage})`,backgroundPosition: `center`,backgroundSize:`cover`
                            }}></div>
                    </div>
                    <div className='board-list-item-write-box'>
                        <div className='board-list-item-nickname'>{writerNickname}</div>
                        <div className='board-list-item-write-date'>{writerDatetime}</div>
                    </div>
                </div>
                <div className='board-list-item-middle'>
                    <div className='board-list-item-title'>{title}</div>
                    <div className='board-list-item-content'>{content}</div>
                </div>
                <div className='board-list-item-bottom'>
                    <div className='board-list-item-counts'>
                        {`댓글 ${commentCount} * 좋아요 ${favoriteCount} * 조회수 ${viewCount}`}
                    </div>
                </div>
            </div>
            {
                boardTitleImage !== null && (
                    <div className='board-list-item-image-box'>
                        <div
                            className='board-list-item-image'
                            style={{
                                background: `url(${boardTitleImage})`,backgroundPosition: `center`,backgroundSize:`cover`
                            }}></div>
                    </div>
                )
            }
        </div>
    )
}
