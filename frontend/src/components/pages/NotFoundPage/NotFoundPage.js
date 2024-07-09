// Node modules
import React, { Component } from 'react';

// Custom modules
import { Link } from 'react-router-dom';
import { withRouter } from "react-router";
import ROUTE_PATHS from 'constants/RoutePaths';

// Styles/CSS
import './NotFoundPage.css';

class NotFoundPage extends Component {
  render() {
    return (
      <div className="not-found-container">
        <h1>404 - Not Found!</h1>
        <p>The page you are looking for does not exist.</p>
        <p>Click <Link to="/">here</Link> to go back to the home page.</p>
      </div>
    );
  }

  componentWillUnmount() {
    localStorage.setItem('selectedCategory', ROUTE_PATHS.DEFAULT_CATEGORY);
  }
}

export default withRouter(NotFoundPage);