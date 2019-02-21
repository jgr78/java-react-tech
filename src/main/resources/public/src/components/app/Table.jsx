import React from 'react';
import { connect } from "react-redux";
import Table from 'react-bootstrap/Table';


const mapStateToProps = state => {
        return { ratings: state.ratings};
      };

const IWTableBlock = (props) => {
     const {ratings} = props;
      if (typeof ratings === "undefined" || ratings === null || !ratings.length) return ("");
      return (
        <div className="table_ratings">
            <Table striped bordered hover >
            <thead>
            <tr>
                <th>Rating</th>
                <th>Percentage</th>
            </tr>
            </thead>
            <tbody className="js-authority-table">
                {!!(ratings)?ratings.map(rating  => (
                    <tr key={rating.name}>
                        <td>{rating.name}</td>
                        <td>{rating.value}%</td>
                    </tr>   
                )):""}
            </tbody>
            </Table>
        </div>
      );
    };

const IWTable = connect(
    mapStateToProps,
    )(IWTableBlock);
export default IWTable;