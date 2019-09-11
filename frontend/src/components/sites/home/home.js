import React, {Component} from 'react';
import './style.scss';

class Home extends Component {
    render() {
        return (
            <React.Fragment>
                <div className={"row"}>
                    <div className={"col-12 col-md-6 homepage header"}>
                    <span>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                    </span>
                        <span>
                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </span>
                    </div>
                    <div className={"col-12 col-md-6"}></div>
                </div>
            </React.Fragment>
        );
    }
}

export default Home;
