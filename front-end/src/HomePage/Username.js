import React, {useState} from 'react';
function Username(props){
  const changeName = event => {
    event.persist();
    let value = event.target.value;
    props.setName(value);
  };
  var display = [];
  if(props.tried){
    display.push(<p> Name must be greater than 0 characters and not
      contain "|"</p>)
  }
  display.push(<input type="text" className="form-control" name="name" placeholder="Name"
                value={props.name}  onChange={changeName}/>)
  return(<form><div className="form-row"><div className="col">{display}</div></div></form>)
}
export default Username;
