import React, { useState } from 'react';
import './App.css';
import BoardItem from 'components/BoardItem';
import { commentListMock, favoriteListMock, latesBoardListMock, top3BoardListMock } from 'mocks';
import Top3Item from 'components/top3Item';
import CommentItem from 'components/CommentItem';
import FavoriteItem from 'components/FavoriteItem';
import InputBox from 'components/InputBox';

function App() {

  const [value, setValue] = useState<string>('');

  return (
    <>
      {/* <InputBox /> */}
    </>
  );
}

export default App;
