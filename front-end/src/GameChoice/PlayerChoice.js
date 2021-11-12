import React from 'react';

function PlayerChoice(prop){
  return(<div class="btn-group" role="group" aria-label="Basic example">
  <button type="button" class="btn btn-secondary" onClick={prop.ai}>Join as AI</button>
  <button type="button" class="btn btn-secondary" onClick={prop.hum}>Join as Human</button>
</div>)
}
export default PlayerChoice;
