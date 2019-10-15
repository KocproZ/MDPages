import React, {Component} from 'react';
import './style.scss';

class Home extends Component {
    render() {
        return (
            <React.Fragment>
                <div className={"row homepage"}>
                    <div className={"col-12 col-md-8 col-xl-12 header"}>Latest news</div>
                    <div className={"col-12 col-md-8 col-xl-6"}>
                        <div className={"box"}>
                            <h1>Hello!</h1>
                            <p>Welcome to MDPages, this project is currently under some serious construction.</p>
                            <p>Nowadays we are rewriting frontend and backend, many features may not work. But over time it will be awesome project!</p>
                        </div>
                    </div>
                    <div className={"col-12 col-md-8 col-xl-6"}>
                        <div className={"box"}>
                            <h1>Hello!</h1>
                            <p>Welcome to MDPages, this project is currently under some serious construction.</p>
                            <p>Nowadays we are rewriting frontend and backend, many features may not work. But over time it will be awesome project!</p>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default Home;
