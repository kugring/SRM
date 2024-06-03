import axios from "axios"; // axios 라이브러리를 import 합니다.
import { SignInRequestDto, SignUpRequestDto } from "./request/auth"; // SignInRequestDto, SignUpRequestDto 타입을 import 합니다.
import { ResponseDto } from "./response"; // ResponseDto 타입을 import 합니다.
import { SignInResponseDto, SignUpResponseDto } from './response/auth'; // SignInResponseDto, SignUpResponseDto 타입을 import 합니다.
import { GetSignInUserResponseDto } from "./response/user";
import { PostBoardRequestDto } from "./request/board";

// API 서버의 기본 도메인을 상수로 정의합니다.
const DOMAIN = 'http://localhost:4000';

// API 서버의 도메인에 버전 정보를 추가하여 기본 API 경로를 상수로 정의합니다.
const API_DOMAIN = `${DOMAIN}/api/v1`;


// 헤더에 접근 권한의 토큰을 넣어주는 코드?
const authorization = (accessToken: string) => {
    return { headers: { Authorization: `Bearer ${accessToken}` } }
};

// 로그인 URL을 생성하는 함수를 정의합니다.
const SIGN_IN_URL = () => `${API_DOMAIN}/auth/sign-in`;

// 회원가입 URL을 생성하는 함수를 정의합니다.
const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`;

// 비동기 함수로 로그인 요청을 처리하는 함수를 정의합니다.
export const signInRequest = async (requestBody: SignInRequestDto) => {
    // 'await'을 사용하여 비동기 작업을 기다립니다.
    // 'axios.post'를 사용하여 로그인 API에 POST 요청을 보냅니다.
    const result = await axios.post(SIGN_IN_URL(), requestBody)
        .then(response => {
            // 응답이 성공적이면 응답 데이터를 SignInResponseDto 타입으로 변환하여 반환합니다.
            const responseBody: SignInResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            // 에러가 발생했을 때, 응답 데이터가 없으면 null을 반환합니다.
            if (!error.response.data) return null;
            // 에러 응답 데이터를 ResponseDto 타입으로 변환하여 반환합니다.
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
    // 결과를 반환합니다.
    return result;
}

export const signUpRequest = async (requestBody: SignUpRequestDto) => {
    // console.log("회원가입 타입 비동기 진행중")
    console.log(SIGN_UP_URL());
    const result = await axios.post(SIGN_UP_URL(), requestBody)

        .then(response => {
            // console.log("then 바디 찾기전")
            const responseBody: SignUpResponseDto = response.data;
            console.log("then 실행중")
            return responseBody;
        })
        .catch(error => {
            // console.log("에러 조건문 돌리기전")
            if (!error.response.data) return null;
            const responseBody: ResponseDto = error.response.data;
            console.log("catch 실행중")

            return responseBody;
        });
    // console.log("return 되기직전")
    // console.log(result) -> 반환되는 데이터는 반환코드와 메세지이다!
    return result;
}

const POST_BOARD_URL = () => `${API_DOMAIN}/board`;

// 로그인한 유저만 받아들어야 하므로 액세스토큰도 같이 받아온다!
export const PostBoardRequest = async (requestBody: PostBoardRequestDto, accessToken: string) =>{
    const result = await axios.post(POST_BOARD_URL(), requestBody, authorization(accessToken))
    .then(response => {
        const responseBody: PostBoardRequestDto = response.data;
        return responseBody;
    })
    .catch(error => {
        const responseBody:ResponseDto = error.response.data;
        return responseBody;
    })
    return result;
} 

const GET_SIGN_IN_USER_URL = () => `${API_DOMAIN}/user`;

export const getSignInUserRequest = async (accessToken: string) => {
    const result = await axios.get(GET_SIGN_IN_USER_URL(), authorization(accessToken))
        .then(response => {
            const responseBody: GetSignInUserResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
    return result;
}

const FILE_DOMAIN = `${DOMAIN}/file`;

const FILE_UPLOAD_URL = () => `${FILE_DOMAIN}/upload`;

const mutipartFormData = { headers: { 'content-Type': 'mulitpart/form-data'} };

export const fileUploadRequest = async (data: FormData) =>{
    const result = await axios.post(FILE_UPLOAD_URL(), data,  mutipartFormData)
    .then(response => {
        const responseBody: string = response.data;
        return responseBody;
    })
    .catch(error =>{
        return null;
    })
    return result;
}