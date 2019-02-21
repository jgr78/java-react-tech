import {
  GET_DATA, CHANGE_AUTHORITY,
} from '../configs/action-types';

export function changeAuthority(selected_authority) {
  // Selected authority changed
  return {
    type: CHANGE_AUTHORITY, selected_authority: selected_authority
  };

}
export function getData(authorities, loaded, message, message_color) {
  // Load the Authorities
  return {
    type: GET_DATA, authorities: authorities, loaded: loaded, message: message, message_color: message_color,
  };
}
