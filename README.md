# Zcommerce : E-Commerce Website Testing Project

<h4>Website Link</h4>
https://zcommerce.crio.do/

<h4>Test Plan Link :</h4>
https://docs.google.com/spreadsheets/d/1sFUPBBR3iNrhNWUVwH1jIxorwMR-UFnJIJVHNsq-kpk/edit?usp=sharing

<h1>Project Overview</h1>
This project focuses on comprehensive testing of an e-commerce website. The testing process is designed to ensure that the website functions correctly, efficiently, and provides a seamless user experience. The testing methodologies implemented in this project include Sanity Testing, Regression Testing, UI Testing, API Testing, and Performance Testing. The Software Testing Life Cycle (STLC) and defect life cycle model were followed rigorously throughout the project to ensure systematic and effective testing.

<h2>Testing Methodologies</h2>

<h3>Sanity Testing</h3>
Sanity testing was conducted to verify that the critical functionalities of the e-commerce application are working as expected. This was done after receiving a new build to ensure that the changes or fixes do not disrupt the existing functionality.

<h3>Regression Testing</h3>
Regression testing was performed to ensure that recent code changes have not adversely affected the existing functionalities of the application. Automated regression test suites were created to facilitate efficient re-testing whenever new updates were introduced.

<h3>UI Testing</h3>
UI testing was carried out to ensure that the user interface of the e-commerce website is intuitive, responsive, and user-friendly. This included checking the layout, design, and interactive elements across various devices and browsers to ensure consistency and responsiveness.

<p>- Tested the registration and login functionality with positive and negative test cases.</p>
<h4>Registration Page</h4>
<img src = "https://github.com/VK14133M02/Zcommerce/assets/106018569/86d24e88-1c45-42d6-a43b-a62e65fc1e37" alt=""/>

<p>- Tested the product search functionality with positive and negative test cases.</p>
<h4>Home Page</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/feb9480a-e93f-4d44-b70e-2e073ed88797" alt="" />
<h4>Product Page</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/89c4fa69-7505-47b8-ae00-0ec33c6e6eaa" alt="" />

<p>- Verified that clicking the "Add to Cart" button adds the product to the cart.</p>
<p>- Verified that clicking the cart button redirects to the cart page, and the product is visible.</p>
<p>- Verified that clicking the + or - button changes the cart quantity and updates the price accordingly.</p>
<p>- Verified that if the cart quantity is 0, the product should not be visible in the cart.</p>
<h4>Cart Page</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/c21867f2-d80b-460b-a588-cb9b2c13e007" alt="" />

<p>- Verified that clicking the purchase button redirects to the checkout page.</p>
<p>- Verified that the address field is present along with payment options on the checkout page.</p>
<h4>Checkout Page</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/66806c94-e12c-4b24-b6d6-e4f376f77896" alt=""/>

<p>- Verified that clicking the order button redirects to the thank you page, displaying the order ID.</p>
<h4>Thanks Page</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/6a62a28c-aff2-406e-b8be-6400be277d62" alt="" />

<h3>API Testing</h3>
API testing was implemented to validate the functionality, reliability, performance, and security of the server-side logic of the application. This included testing RESTful APIs to ensure they return the correct responses for various requests, handle errors gracefully, and perform efficiently under different conditions.
<h4>Order Services : http://order-service-prod.zcommerce.crio.do/swagger-ui/index.html</h4>

- POST Request on Cart : Able to add a product to the cart using cart-id and item-id.
- GET Request on Cart : Retrieve the items in the cart and verify that the item-id is correct.
- PATCH Request on Cart : Update the cart quantity, ensuring it updates accordingly.
- DELETE Request on Cart : Remove an item from the cart.
<h4>Cart API</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/64e7f217-8489-4c45-88cc-1ca3b16c6d35" alt="" />

- Create Order : Directly create an order with the item-id, generating an order-id with a POST request on the order.
- GET Request on Order : Retrieve and verify the order details, including item-id and quantity.
- PATCH and DELETE Requests on Order : Update or delete the order as needed.
<h4>Payment API</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/1353d13c-48da-4298-af79-ed9da99c14c9" alt="" />

- POST Request on Payment : Generate a payment-id and transaction number using the order-id.
- GET, PATCH, and DELETE Requests on Payment: Update or delete the payment.
<h4>Payment API</h4>
<img  src="https://github.com/VK14133M02/Zcommerce/assets/106018569/3c6c504c-8422-4bad-a25c-bd0c8b1f8b13" alt=""/>

<h4>Product Services : http://product-service-prod.zcommerce.crio.do/swagger-ui/index.html</h4>
- POST Request on Product : Add a product to the database.
- GET Request on Product : Retrieve products using multiple filters, such as item-id and category.
- PUT Request on Product : Update product details like name, price, quantity, and review.
- DELETE Request on Product : Remove a specific item from the database using the item-id.
<h4>Product API</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/f305106b-e601-42cd-9486-4368d57c13e4" alt="" />

Review Management : Perform GET, POST, and PATCH requests on cart reviews to verify posting, updating, or deleting reviews.
<h4>Review API</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/a49c75ba-f59f-42e1-a23d-eaa8ca34e207" alt="" />
<h4>Category API</h4>
<img src="https://github.com/VK14133M02/Zcommerce/assets/106018569/b992f8cf-f9c3-43aa-aef5-44ddb5d73b38" alt="" />

<h3>Performance Testing</h3>
Performance testing was conducted to determine how the application performs under various load conditions. This included stress testing, load testing, and endurance testing to identify potential bottlenecks, ensure stability under heavy traffic, and confirm that the application can handle the expected user load without performance degradation.

<h2>Software Testing Life Cycle (STLC)</h2>
The project followed the STLC model, which includes the following phases:
<h3>Requirement Analysis</h3>
Understanding the functional and non-functional requirements of the e-commerce application.
<h3>Test Planning</h3>
Developing a test strategy, defining the scope of testing, and preparing a detailed test plan outlining the resources, schedule, and deliverables.
<h3>Test Case Development</h3>
Creating detailed test cases and test scripts based on the defined requirements and scenarios to ensure comprehensive coverage of all functionalities.
<h3>Test Environment Setup</h3>
Configuring the necessary hardware and software environments, setting up databases, and preparing test data for executing the test cases.
<h3>Test Execution</h3>
Executing the test cases, reporting defects, and re-testing the fixed issues. Automated tests were run to expedite the regression testing process.
<h3>Test Closure</h3>
Preparing the test summary report, documenting lessons learned, and obtaining project sign-off from stakeholders. Ensuring that all test artifacts are archived for future reference.

<h2>Tools and Technologies</h2>
<h3>UI Testing: Java,Selenium, TestNG </h3> 
<h3>API Testing: Postman, REST Assured, Swagger </h3> 
<h3>Performance Testing: JMeter, LoadRunner </h3>
<h3>Tool: Log4J, Extent Report</h3> 
<h3>Continuous Integration: GitHub Actions</h3>

<h2>Conclusion</h2>
This project demonstrates a thorough approach to testing an e-commerce website, ensuring that it meets high standards of quality and performance. By following the STLC model and employing various testing methodologies, we have aimed to deliver a robust, reliable, and user-friendly application.

<p>For more details on the implementation and to view the test scripts, please refer to the project repository.</p>
























