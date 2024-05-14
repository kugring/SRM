import React, { ChangeEvent, useState } from 'react'
import './style.css'
import { useNavigate } from 'react-router-dom'
import { MAIN_PATH } from 'constant'

//          component: 헤더 레이어웃            //
export default function Header() {

  //          function: 네비게이트 함수           //
  const navigate = useNavigate()

  //          event handler: 로고 클릭 이벤트 처리 함수           //
  const onLogoClickHandler = () => {
    navigate(MAIN_PATH())
  }
  //          component: 검색 버튼 컴포넌트            //
  const SearchButton = () => {

    //          state: 검색 버튼 상태           //
    const [status, setStatus] = useState<boolean>(false);
    //          state: 검색어 상태           //
    const [searchWord, setSearchWord] = useState<string>('');

    //          event handler: 검색 아이콘 클릭 이벤트 처리 함수           //
    const onSearchWordChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      const value = event.target.value;
      setSearchWord(value);
    }
    //          event handler: 검색 아이콘 클릭 이벤트 처리 함수           //
    const onSearchButtonClickHandler = () => {
      if (!status) {
        setStatus(!status);
        return;
      }
    }

    if (!status)
      //          render: 검색 버튼 컴포넌트 렌더링 ( 클릭 false 상태 )          //
    return (
    <div className='icon-button' onClick={onSearchButtonClickHandler}>
      <div className='icon search-light-icon'></div>
    </div>
    );
    //          render: 검색 버튼 컴포넌트 렌더링 ( 클릭 true 상태 )          //
    return (
    <div className='header-search-input-box'>
      <input className='header-search-input' type='text' placeholder='검색어를 입력해주세요.' value={searchWord} onChange={onSearchWordChangeHandler}/>
      <div className='icon-button'>
        <div className='icon search-light-icon'></div>
      </div>
    </div>
    );

  }

  //          render: 헤더 레이어웃 렌더링           //
  return (
    <div id='header'>
      <div className='header-container'>
        <div className='header-left-box' onClick={onLogoClickHandler}>
          <div className='icon-box'>
            <div className='icon logo-dark-icon'></div>
          </div>
          <div className='header-logo'>{'Kugring Board'}</div>
        </div>
        <div className='header-right-box'>
          <SearchButton />
        </div>
      </div>
    </div>
  )
}
