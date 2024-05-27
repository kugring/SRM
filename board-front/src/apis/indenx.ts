import axios from "axios";
import { SignInRequestDto, SignUpRequestDto } from "./request/auth";
import { ResponseDto } from "./response";
import { SignInResponseDto } from './response/auth';

const DOMAIN = 'http://localhost:4000';

const API_DOMAIN = `${DOMAIN}/api/v1`;

const SIGN_IN_URL = () => `${API_DOMAIN}/auth/sign-in`
const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`

// 비동기함수 async
export const signInRequest = async (requestBody: SignInRequestDto) => {
    // await을 걸어서 해당 동작을 기다리겠다라는 의미
    console.log("응답을 하긴함???")

    const result = await axios.post(SIGN_IN_URL(), requestBody)
        .then(response => {
            const responseBody: SignInResponseDto = response.data;
            console.log("then이 응답함" + response.data)
            return responseBody;
        })
        .catch(error => {
            if (!error.response.data) return null;
            const responseBody: ResponseDto = error.response.data;
            console.log("catch가 응답함" + error.response.data)

            return responseBody;
        })
    return result;
}


export const signUpReqiest = async (requestBody: SignUpRequestDto) => {

}