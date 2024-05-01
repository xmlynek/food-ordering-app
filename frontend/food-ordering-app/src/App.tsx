import {Card, Result, Spin} from "antd";
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import {lazy, Suspense} from "react";
import LayoutWrapper from "./layout/LayoutWrapper/LayoutWrapper.tsx";

const HomePage = lazy(() => import("./pages/HomePage.tsx"));
const RestaurantsListPage = lazy(() => import("./pages/RestaurantsListPage.tsx"));
const RestaurantPage = lazy(() => import("./pages/RestaurantPage.tsx"));
const BasketPage = lazy(() => import("./pages/BasketPage.tsx"));
const CheckoutPage = lazy(() => import("./pages/CheckoutPage.tsx"));
const ProfilePage = lazy(() => import("./pages/ProfilePage.tsx"));
const OrderHistoryPage = lazy(() => import("./pages/OrderHistoryPage.tsx"));
const OrderDetailsPage = lazy(() => import("./pages/OrderDetailsPage.tsx"));

function App() {

  return (
      <BrowserRouter>
        <LayoutWrapper>
          <Suspense fallback={<Spin fullscreen spinning={true}/>}>
            <Routes>
              <Route path="/" element={<HomePage/>}/>
              <Route path="/restaurants" element={<RestaurantsListPage/>}/>
              <Route path="/restaurants/:id" element={<RestaurantPage/>}/>
              <Route path="/basket" element={<BasketPage/>}/>
              <Route path="/checkout" element={<CheckoutPage/>}/>
              <Route path="/profile" element={<ProfilePage/>}/>
              <Route path="/order-history" element={<OrderHistoryPage/>}/>
              <Route path="/order-history/:orderId" element={<OrderDetailsPage/>}/>
              <Route path="*" element={<Card>
                <Result
                    status="404"
                    title="404"
                    subTitle="Sorry, the page you visited does not exist."
                    extra={<Link to={"/"}>Back Home</Link>}
                />
              </Card>}/>
            </Routes>
          </Suspense>
        </LayoutWrapper>
      </BrowserRouter>
  )
}

export default App
