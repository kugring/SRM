export default interface BoardListItem {
    boardNumber: number;
    title: string;
    content: string;
    boardTitleImage: string | null; // 게시글의 이미지가 없을 수도 있음!
    favoriteCount: number;
    commentCount: number;
    viewCount: number;
    writerDatetime: string;
    writerNickname: string;
    writerProfileImage: string | null; // 프로필의 이미지가 디폴트 값이 있을 수도 있음!
}
