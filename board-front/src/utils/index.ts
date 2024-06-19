export const convertUrlToFile = async (url: string) => {
    const response = await fetch(url);
    const data = await response.blob();
    const extend = url.split('.').pop();
    const fileName = url.split('/').pop();
    const meta = {type: `image/${extend}` };

    // 특정한 url을 받아와서 실제 파일로 받아와서 파일객체로 바꿔주는 역할
    return new File([data], fileName as string, meta);
}

export const convertUrlsToFile = async (urls: string[]) => {
    const files: File[] = [];
    for (const url of urls) {
        const file = await convertUrlToFile(url);
        files.push(file);
    }
    return files;
}
