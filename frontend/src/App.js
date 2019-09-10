import React, {Component} from 'react';
import Navigation from "./components/navigation";
import Container from "./components/container";
import {BrowserRouter as Router, Switch} from "react-router-dom";

class App extends Component {
    render() {
        return (
            <div className="App">
                <Navigation/>
                <Router>
                    <Switch>
                        <Container/>
                    </Switch>
                </Router>
            </div>
        );
    }
}

export default App;
