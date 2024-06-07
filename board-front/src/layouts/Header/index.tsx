import React, { ChangeEvent, KeyboardEvent, useEffect, useRef, useState } from 'react'
import './style.css'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { AUTH_PATH, BOARD_DETAIL_PATH, BOARD_PATH, BOARD_UPDATE_PATH, BOARD_WRITE_PATH, MAIN_PATH, SEARCH_PATH, USER_PATH } from 'constant'
import { useCookies } from 'react-cookie'
import { useBoardStore, useLoginUserStore } from 'stores'
import { postBoardRequest, fileUploadRequest } from 'apis'
import { PostBoardRequestDto } from 'apis/request/board'
import { ResponseDto } from 'apis/response'
import { PostBoardResponseDto } from 'apis/response/board'

//          component: 헤더 레이어웃            //
export default function Header() {

  //          state: 로그인 유저 상태           //
  const { loginUser, setLoginUser, resetLoginUser } = useLoginUserStore();
  //          state: path 상태           //
  const { pathname } = useLocation();
  //          state: cookie 상태           //
  const [cookies, setCookie] = useCookies();
  //          state: 로그인 상태            //
  const [isLogin, setLogin] = useState<boolean>(false);
  //          state: 인증 페이지 상태            //
  const [isAuthPage, setAuthPage] = useState<boolean>(false);
  //          state: 메인 페이지 상태            //
  const [isMainPage, setMainPage] = useState<boolean>(false);
  //          state: 검색 페이지 상태            //
  const [isSearchPage, setSearchPage] = useState<boolean>(false);
  //          state: 게시물 상세 페이지 상태            //
  const [isBoardDetailPage, setBoardDetailPage] = useState<boolean>(false);
  //          state: 게시물 작성 페이지 상태            //
  const [isBoardWritePage, setBoardWritePage] = useState<boolean>(false);
  //          state: 게시물 수정 페이지 상태            //
  const [isBoardUpdatePage, setBoardUpdatePage] = useState<boolean>(false);
  //          state: 유저 페이지 페이지 상태            //
  const [isUserPage, setUserPage] = useState<boolean>(false);


  //          function: 네비게이트 함수           //
  const navigate = useNavigate()

  //          event handler: 로고 클릭 이벤트 처리 함수           //
  const onLogoClickHandler = () => {
    navigate(MAIN_PATH())
  }
  //          component: 검색 버튼 컴포넌트            //
  const SearchButton = () => {

    //          state: 검색 버튼 입력 요소 상태           //
    const searchButtonWordRef = useRef<HTMLInputElement | null>(null);
    //          state: 검색 버튼 상태           //
    const [status, setStatus] = useState<boolean>(false);
    //          state: 검색어 상태           //
    const [word, setWord] = useState<string>('');
    //          state: 검색어 path variable 상태           //
    const { searchWord } = useParams();

    //          event handler: 검색어 변경 이벤트 처리 함수           //
    const onSearchWordChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      const value = event.target.value;
      setWord(value);
    }
    //          event handler: 검색어 키 이벤트 처리 함수           //
    const onSearchWordKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!searchButtonWordRef.current) return;
      searchButtonWordRef.current.click();
    }
    //          event handler: 검색어 버튼 클릭 이벤트 처리 함수           //
    const onSearchButtonClickHandler = () => {
      if (!status) {
        setStatus(!status);
        return;
      }
      navigate(SEARCH_PATH(word));
    }

    //          effect: 검색어 path variable 변경 될때 마다 실행될 함수           //
    useEffect(() => {
      if (searchWord) {
        setWord(searchWord);
        setStatus(true);
      }
    }, [searchWord]);

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
        <input className='header-search-input' type='text' placeholder='검색어를 입력해주세요.' value={word} onChange={onSearchWordChangeHandler} onKeyDown={onSearchWordKeyDownHandler} />
        <div ref={searchButtonWordRef} className='icon-button' onClick={onSearchButtonClickHandler}>{/* 검색어를 입력하고 버튼을 누르면 해당 value가 url로 넘어가는데 SEARCH_PATH로 넘어간다. */}
          <div className='icon search-light-icon'></div>
        </div>
      </div>
    );
  }
  //          component: 로그인 또는 마이페이지 버튼 컴포넌트            //
  const MypageButton = () => {
    //          state: userEmail path variable 상태            //
    const { userEmail } = useParams();
    //          event handler: 마이페이지 버튼 클릭 이벤트 처리 함수            //
    const onMyPageButtonClickHandler = () => {
      if (!loginUser) return;
      const { email } = loginUser;
      navigate(USER_PATH(email));
    }
    //          event handler: 마이페이지 버튼 클릭 이벤트 처리 함수            //
    const onSignOutButtonClickHandler = () => {
      resetLoginUser();
      setCookie('accessToken', '', { path: MAIN_PATH(), expires: new Date() })  // 로그아웃 진행시 setCookie를 통해서 accessToken 쿠키가 날라간다!
      navigate(MAIN_PATH());
    }
    //          event handler: 마이페이지 버튼 클릭 이벤트 처리 함수            //
    const onSignInButtonClickHandler = () => {
      navigate(AUTH_PATH());
    }

    if (isLogin && userEmail === loginUser?.email)   // "?"를 쓰는 이유는 loginUser가 null인 경우에 런타임오류를 방지하기 위한 typeScript의 Optional Chaining 이다!
      //          render: 로그아웃 버튼 컴포넌트 렌더링           //
      return <div className='white-button' onClick={onSignOutButtonClickHandler}>{'로그아웃'}</div>;
    if (isLogin)
      //          render: 마이페이지 버튼 컴포넌트 렌더링           //
      return <div className='white-button' onClick={onMyPageButtonClickHandler}>{'마이페이지'}</div>;
    //          render: 로그인 버튼 컴포넌트 렌더링           //
    return <div className='black-button' onClick={onSignInButtonClickHandler}>{'로그인'}</div>;
  }

  //          component: 업로드 버튼 컴포넌트            //
  const UploadButton = () => {
    //          state: 게시물 상태           //
    const { title, content, boardImageFileList, resetBoard } = useBoardStore();

    //            function: post board response 처리 함수           //
    const postBoardResponse = (responseBody: PostBoardResponseDto | ResponseDto | null) => {
      if (!responseBody) return;
      const { code } = responseBody;
      if (code === 'DBE') alert('데이터베이스 오류입니다.');
      if (code === 'AF' || code == 'NU') navigate(AUTH_PATH());
      if (code === 'VF') alert('제목과 내용은 필수입니다.');
      if (code !== 'SU') return;

      resetBoard();
      if(!loginUser) return;
      const { email } = loginUser;
      // 업로드 이후 마이페이지로 이동하게 한다.
      navigate(USER_PATH(email));
    }

    //          event handler: 업로드 버튼 클릭 이벤트 처리 함수            //
    const onUploadButtonClickHandler = async () => {
      const accessToken = cookies.accessToken;
      if (!accessToken) return;

      const boardImageList: string[] = [];

      for (const file of boardImageFileList) {
        const data = new FormData();
        data.append('file', file);

        const url = await fileUploadRequest(data);
        if (url) boardImageList.push(url);
      }

      const requestBody: PostBoardRequestDto = {
        title, content, boardImageList
      }
      postBoardRequest(requestBody, accessToken).then(postBoardResponse);
    }
    //          render: 업로드 버튼 컴포넌트 렌더링           //
    if (title && content)
      return <div className='black-button' onClick={onUploadButtonClickHandler}>{'업로드'}</div>
    //          render: 업로드 불가 버튼 컴포넌트 렌더링           //
    return <div className='disable-button'>{'업로드'}</div>
  }

  //          effect: path가 변경 될때 마다 실행될 함수           //
  useEffect(() => {
    const isAuthPage = pathname.startsWith(AUTH_PATH());
    setAuthPage(isAuthPage);
    const isMainPage = pathname === MAIN_PATH();
    setMainPage(isMainPage);
    const isSearchPage = pathname.startsWith(SEARCH_PATH('')); // 해당 문자열로 시작하는지를 확인해주는 startSWith라는 함수이다.
    setSearchPage(isSearchPage);
    const isBoardDetailPage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_DETAIL_PATH(''))
    setBoardDetailPage(isBoardDetailPage);
    const isBoardWritePage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_WRITE_PATH())
    setBoardWritePage(isBoardWritePage);
    const isBoardUpdatePage = pathname.startsWith(BOARD_PATH() + '/' + BOARD_UPDATE_PATH(''))
    setBoardUpdatePage(isBoardUpdatePage);
    const isUserPage = pathname.startsWith(USER_PATH(''))
    setUserPage(isUserPage);
  }, [pathname])
  //          effect: login user가 변경 될때 마다 실행될 함수           //
  useEffect(() => {
    setLogin(loginUser !== null);
  }, [loginUser])

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
          {(isAuthPage || isMainPage || isSearchPage || isBoardDetailPage) && <SearchButton />}
          {(isMainPage || isSearchPage || isBoardDetailPage || isUserPage) && <MypageButton />}
          {(isBoardWritePage || isBoardUpdatePage) && <UploadButton />}
        </div>
      </div>
    </div>
  )
}
