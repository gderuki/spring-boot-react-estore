// Node modules
import React, { Component } from 'react';
import { withRouter } from "react-router";
import { Route, Redirect, Switch } from 'react-router-dom';

// Custom Modules
import ROUTE_PATHS from 'constants/RoutePaths';
import StickyNavbar from 'organisms/StickyNavbar';
import ProductListing from 'pages/ProductListing';
import ProductDetailPage from 'pages/ProductDetailPage';
import NotFoundPage from 'pages/NotFoundPage';
import { CartProvider } from 'contexts/CartContext';

// Styles/CSS
import './App.css';

class App extends Component {
  state = {
    displayNavbar: true,
    isCartOverlayVisible: false,
    selectedCategoryName: '',
  };

  render() {
    return (
      <CartProvider>
        {this.state.displayNavbar
          ? <StickyNavbar
            selectedCategoryName={this.state.selectedCategoryName}
            isCartOverlayVisible={this.state.isCartOverlayVisible}
            toggleCartOverlay={this.toggleCartOverlay}
            selectCategory={this.selectCategory}
          />
          : null
        }
        {this.state.isCartOverlayVisible && <div className="backdrop" onClick={this.toggleCartOverlay}></div>}
        <Switch>
          <Route exact path={ROUTE_PATHS.HOME} render={() => <Redirect to={ROUTE_PATHS.DEFAULT_REDIRECT} />} />
          <Route path={ROUTE_PATHS.PRODUCT} render={(props) => <ProductDetailPage {...props} toggleCartOverlay={this.toggleCartOverlay} />} />
          <Route path={ROUTE_PATHS.CATEGORY} render={(props) => <ProductListing {...props} toggleCartOverlay={this.toggleCartOverlay} />} />
          <Route path={ROUTE_PATHS.NOT_FOUND} component={NotFoundPage} />
          <Route render={() => <Redirect to={ROUTE_PATHS.NOT_FOUND} />} />
        </Switch>
      </CartProvider>
    );
  }

  componentDidMount() {
    this.unlisten = this.props.history.listen((location, action) => {
      this.handleRouteChange(location);
    });
    this.handleRouteChange(this.props.location);
  }

  componentDidUpdate(prevProps) {
    if (this.props.location !== prevProps.location) {
      this.handleRouteChange(this.props.location);
    }
  }

  componentWillUnmount() {
    if (this.unlisten) {
      this.unlisten();
    }
  }

  handleRouteChange = (location) => {
    this.setState({ displayNavbar: location.pathname !== ROUTE_PATHS.NOT_FOUND });
  }

  toggleCartOverlay = () => {
    this.setState(prevState => ({
      isCartOverlayVisible: !prevState.isCartOverlayVisible
    }));
  };

  selectCategory = (newCategoryName) => {
    this.setState({ selectedCategoryName: newCategoryName });
    localStorage.setItem('selectedCategory', newCategoryName);
  }
}

export default withRouter(App);