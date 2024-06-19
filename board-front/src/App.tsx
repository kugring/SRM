import './App.css';
import { Route, Routes } from 'react-router-dom';
import Main from 'views/Main';
import Authentication from 'views/Authentication';
import UserP from 'views/User';
import Search from 'views/Search';
import Container from 'layouts/Container';
import BoardDetail from 'views/Board/Detail';
import BoardUpdate from 'views/Board/Update';
import BoardWrite from 'views/Board/Write';
import { AUTH_PATH, BOARD_DETAIL_PATH, BOARD_PATH, BOARD_UPDATE_PATH, BOARD_WRITE_PATH, MAIN_PATH, SEARCH_PATH, USER_PATH } from 'constant';
import { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useLoginUserStore } from 'stores';
import { getSignInUserRequest } from 'apis';
import { GetSignInUserResponseDto } from 'apis/response/user';
import { ResponseDto } from 'apis/response';
import { User } from 'types/interface';


//          component: Application 컴포넌트       //
function App() {


  //            state: 로그인 유저 전역 상태            //
  const { setLoginUser, resetLoginUser } = useLoginUserStore();
  //            state: cookie 상태            //
  const [cookies, setCookie] = useCookies();

  //            function: get sign in user response 처리 함수           //
  const getSignInUserResponse = (responseBody: GetSignInUserResponseDto | ResponseDto | null) => {
    if(!responseBody) return;
    const { code } = responseBody;
    if(code === 'AF' || code === 'NU' || code === 'DBE' ){
      resetLoginUser();
      return;
    }
    //스프레드 연산자(Spread Operator)는 JavaScript와 TypeScript에서 객체나 배열의 요소를 확장(expand)하는데 사용되는 구문입니다
    // ...responseBody에 존재하는 User데이터를 그대로 전달한다.
    const loginUser: User = { ...responseBody as GetSignInUserResponseDto };
    setLoginUser(loginUser);
  }
  //            effect: accessToken cookie 값이 변경될 때 마다 실행할 함수              //
  useEffect(() => {
    if(!cookies.accessToken){
      resetLoginUser();
      return;
    }
    getSignInUserRequest(cookies.accessToken).then(getSignInUserResponse)
  }, [cookies.accessToken]); //쿠키 객체에서 accessToken이라는 이름의 쿠키 값을 의미

  //          render: Application 컴포넌트 렌더링       //

  //          description: 메인 화면 : '/' - Main       //
  //          description: 로그인 + 회원가입 : '/auth' - Authentication       //
  //          description: 검색 화면 : '/search/:serchWord' - Search       //
  //          description: 유저 페이지 : '/user/:userEmail' - User       //
  //          description: 게시물 상세보기 : '/board/detail/:boardNumber' - BoardDetail       //
  //          description: 게시물 작성하기 : '/board/write' - BoardWrite       //
  //          description: 게시물 수정하기 : '/board/update/:boardNumber' - BoardUpdate       //
  return (
    <Routes>
      <Route element={<Container />}>
        <Route path={MAIN_PATH()} element={<Main />} />
        <Route path={AUTH_PATH()} element={<Authentication />} />
        <Route path={SEARCH_PATH(':searchWord')} element={<Search />} />
        <Route path={USER_PATH(':userEmail')} element={<UserP />} />
        <Route path={BOARD_PATH()}>
          <Route path={BOARD_WRITE_PATH()} element={<BoardWrite />} />
          <Route path={BOARD_DETAIL_PATH(':boardNumber')} element={<BoardDetail />} />
          <Route path={BOARD_UPDATE_PATH(':boardNumber')} element={<BoardUpdate />} />
        </Route>
        {/* 오류가 있는 url주소로 들어오는 경우 */}
        < Route path='*' element={<h1>404 Not Found</h1>} />
      </Route>
    </Routes>
  );
}

export default App;
