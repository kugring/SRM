import { create } from "zustand";


// 보드에서 업로드 버튼을 누르기 위한 전역변수로 지정해놓은 코드이다.
interface BoardStore {
    title: string;
    content: string;
    boardImageFileList: File[];
    setTitle: (title: string) => void;
    setContent: (content: string) => void;
    setBoardImageFileList: (boardImageFileList: File[]) => void;
    resetBoard: () => void;
};

const useBoardStore = create<BoardStore>(set => ({
    title: '',
    content: '',
    boardImageFileList: [],
    setTitle: (title) => set(state => ({...state, title})),
    setContent: (content) => set(state => ({...state, content})),
    setBoardImageFileList: (boardImageFileList) => set(state => ({...state, boardImageFileList})),
    resetBoard: () => set(sate => ({...StaticRange, title: '', content: '', boardImageFileList: []}))
}));

export default useBoardStore;