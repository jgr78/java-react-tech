import {
  GET_DATA, CHANGE_AUTHORITY, ERR_PARSING,
} from '../configs/action-types';

export const getInitialState = () => ({
  // initial state
  authorities: [],
  selected_authority: '',
  message: '',
  message_color: '',
  loaded: false,
  ratings: [],
});

export default (state, action) => {
  // reducers
  switch (action.type) {
    case GET_DATA:
      return Object.assign({}, state, {
        authorities: action.authorities,
        message: action.message,
        loaded: action.loaded,
      });
    case CHANGE_AUTHORITY:
      return Object.assign({}, state, {
        selected_authority: action.selected_authority,
        ratings: action.ratings,
        message: action.message,
      });
    case ERR_PARSING:
      return Object.assign({}, state, {
        message: action.message,
        message_color: action.message_color,
    });
    default:
      return state;
  }
};
