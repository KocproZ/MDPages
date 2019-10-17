import React, {Component} from 'react';
import './style.scss';

class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            search: ''
        };
        this.handleSearch = this.handleSearch.bind(this);
        this.handleTextSearch = this.handleTextSearch.bind(this);
    }

    handleSearch(event) {
        event.preventDefault();
        //TODO: do search with variable: event.target.search.value
    }

    handleTextSearch(event) {
        event.preventDefault();
        this.setState({search: event.target.value})
    }

    render() {
        return (
            <form className={"search"} onSubmit={this.handleSearch} autoComplete={"off"}>
                <input value={this.state.search} name="search" type={"text"} onChange={this.handleTextSearch} placeholder={"Type text to search..."}/>
                <input type={"submit"} className={"hidden"}/>
            </form>
        );
    }
}

export default Index;
