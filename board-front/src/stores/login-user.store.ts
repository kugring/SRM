import { User } from 'types/interface';
import { create } from 'zustand';

interface LoginUserStore {
    loginUser: User | null; 
    setLoginUser: (LoginUser: User) => void;
    resetLoginUser: () => void;
}
// 전역으로 state를 남겨 놓으면 다른 페이지 마다 상태를 상속할 필요없이 한번에 가능하다.
const useLoginUserStore = create<LoginUserStore>(set => ({
    loginUser: null,
    setLoginUser: (loginUser) => set(state => ({...state, loginUser})),
    resetLoginUser: () => set(state => ({...state, loginUser: null }))
}));

export default useLoginUserStore;