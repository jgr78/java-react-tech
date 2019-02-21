import React from "react";
import { connect } from "react-redux";
import PropTypes from 'prop-types';
import styled from 'styled-components';
import { style } from '../../configs/theme';
import { DATA_LOADING} from "../../configs/messages";

const mapStateToProps = state => {
  const message_aux = (typeof state.message === "undefined") ? DATA_LOADING : state.message;
  return { message: message_aux, message_color: state.message_color };
};

const MessageStyled = styled.section`
  color: ${(props) => props.message_color ? style(props.message_color)  : style("color.tertiary")};
  display: block;
  font-family: ${style('headerFontFamily')};
  font-size: ${style('fontSize.normal')};
  font-weight: ${style('headerFontWeight')};
  line-height: ${style('fontSize.normal')};
  padding: ${style('paddingHalf')};
  position: relative;
  text-decoration: none;
  margin-left: ${style('marginHalf')};
`;

const MessageBlock = ({ message, ...props }) => (
  <MessageStyled {...props} >
    {message}
  </MessageStyled>
);

MessageBlock.propTypes = {
  message: PropTypes.string,
  color: PropTypes.string,
};

MessageBlock.defaultProps = {
  message: ''
};

const Message = connect(
  mapStateToProps
)(MessageBlock);
export default Message;