# Production stage - Serve static files directly
FROM nginx:alpine

# Copy our static HTML, CSS, JS files
COPY public/ /usr/share/nginx/html/

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"] 