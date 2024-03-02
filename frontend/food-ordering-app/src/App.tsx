import {Layout, Result, Spin} from "antd";
import Navbar from "./layout/navbar/Navigation.tsx";
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import {lazy, Suspense} from "react";
import AppFooter from "./layout/footer/AppFooter.tsx";

const HomePage = lazy(() => import("./pages/HomePage.tsx"));
const RestaurantsListPage = lazy(() => import("./pages/RestaurantsListPage.tsx"));
const RestaurantPage = lazy(() => import("./pages/RestaurantPage.tsx"));
const BasketPage = lazy(() => import("./pages/BasketPage.tsx"));


function App() {

  return (
      <BrowserRouter>
        <Layout style={{
          overflow: 'auto',
          minHeight: '100vh',
          width: '100%',
          left: 0,
        }}>
          <Navbar/>
          <Layout>
            <Suspense fallback={<Spin fullscreen spinning={true}/>}>
              <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/restaurants" element={<RestaurantsListPage/>}/>
                <Route path="/restaurants/:id" element={<RestaurantPage/>}/>
                <Route path="/basket" element={<BasketPage/>}/>
                <Route path="*" element={<Result
                    status="404"
                    title="404"
                    subTitle="Sorry, the page you visited does not exist."
                    extra={<Link to={"/"}>Back Home</Link>}
                />}/>
              </Routes>
            </Suspense>
          </Layout>
          <AppFooter/>
        </Layout>
      </BrowserRouter>

  )
}

export default App
