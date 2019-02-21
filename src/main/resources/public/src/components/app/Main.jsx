import React from "react";
import Select from './Select';
import Table from './Table';
import Message from './Message';


export default () => (
    <main role="main" className="inner cover">
        <h1 className="cover-heading">Food Agency Data Summarizer.</h1>
        <p className="lead">
            This summarizer will take take all the data from a specific authority, and show you how the food
            hygiene ratings are distributed.
        </p>
        <div>
            <label> Select an Authority:</label>
            <Select></Select>
            <Message></Message>
            <Table></Table>
            
        </div>
    </main>
)