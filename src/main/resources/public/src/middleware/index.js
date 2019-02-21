import { GET_DATA, CHANGE_AUTHORITY, ERR_PARSING } from "../configs/action-types";
import { DATA_UPDATED, RATING_UPDATED, ERR_LOADING } from "../configs/messages";
import { API_URL } from "../configs/props.js";

export function getDataMiddleware({ dispatch }) {
  return next => action => {
    if (action.type === GET_DATA) {
      fetch(API_URL)
      .then(res => res.json())
      .then(
        (result) => {
          action.loaded = true;
          action.message = DATA_UPDATED;
          action.authorities= result;
          action.selected_authority="";
          next(action);
        },
        (error) => {
          console.log(error);
          return dispatch({ type: ERR_PARSING, message: ERR_LOADING, message_color: 'color.quaternary'});
        }
      )
      next(action);
    } else if (action.type === CHANGE_AUTHORITY) {
      fetch(API_URL + "/" + action.selected_authority)
      .then(res => res.json())
      .then(
        (result) => {
          action.message = RATING_UPDATED;
          action.ratings= result;
          next(action);
        },
        (error) => {
          console.log(error);
          return dispatch({ type: ERR_PARSING, message: ERR_LOADING, message_color: 'color.quaternary'});
        }
      )
      next(action);
    } else if (action.type === ERR_PARSING) {
        action.message = ERR_LOADING;
        action.message_color = 'color.quaternary';
        next(action);
    }
  }
}

