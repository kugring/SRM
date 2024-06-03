import { User } from 'types/interface';
import ResponseDto from '../response.dto';

// 이전에 User라는 Ts를 만들어놓은것을 그대로 extends(확장)해서 사용하겠다! -> 해당 변수들이 같이 때문이다!
export default interface GetSignInUserResponseDto extends ResponseDto, User {

}