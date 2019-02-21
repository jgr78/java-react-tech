import React from 'react';
import { connect } from "react-redux";
import { changeAuthority } from "../../actions/index";


function mapDispatchToProps(dispatch) {
  return {
    changeAuthority: authority => dispatch(changeAuthority(authority))
  };
};

const mapStateToProps = state => {
  return { authorities: state.authorities, selected_authority: state.selected_authority };
};

const IWSelectBlock = (props) => {
  const {authorities, selected_authority} = props;
  return (
    <div className="js-authority-select">
      <select  onChange={(e) => props.changeAuthority(e.target.value)}>
        <option value="">Select Authority... </option>
        {authorities.map(authority  => (
          <option key={authority.id} value={authority.id} >
            {authority.name}
          </option>
        ))}
      </select>
    </div>
  );
};

const IWSelect = connect(
    mapStateToProps,
    mapDispatchToProps,
    )(IWSelectBlock);
export default IWSelect;