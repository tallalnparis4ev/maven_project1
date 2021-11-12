import React from 'react';

function SortButtons(props){
  function changeSort(newSort){
    return () => {if(props.sort !== newSort) props.setSort(newSort);}
  }
  return(
    <div>
    <button onClick={changeSort(1)}> Sort by suit </button>
    <button onClick={changeSort(2)}> Sort by rank </button>
    <button onClick={changeSort(3)}> Sort by rank & suit </button>
    </div>
  )
}

export default SortButtons;
