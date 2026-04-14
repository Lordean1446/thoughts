class ThoughtsApp {
    constructor() {
        this.posts = [];
        this.apiBase = '/posts';
        // TODO: Substituir por usuário real após implementar Spring Security
        this.currentUser = { id: '1', name: 'Maria Brown' }; // Deve coincidir com o usuário do AuthService
        this.init();
    }

    init() {
        this.bindEvents();
        this.loadPosts();
    }

    bindEvents() {
        // Form submission
        document.getElementById('thoughtForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.createPost();
        });

        // Search
        document.getElementById('searchInput').addEventListener('input', (e) => {
            this.searchPosts(e.target.value);
        });
    }

    async loadPosts() {
        try {
            this.showLoading();
            const response = await fetch(this.apiBase);
            this.posts = await response.json();
            this.renderPosts();
        } catch (error) {
            this.showError('Erro ao carregar pensamentos: ' + error.message);
        }
    }

    async createPost() {
        const contentInput = document.getElementById('thoughtContent');
        const content = contentInput.value.trim();

        if (!content) {
            this.showError('Por favor, escreva seu pensamento');
            return;
        }

        try {
            const post = {
                body: content,
                date: new Date().toISOString()
            };

            const response = await fetch(this.apiBase, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(post)
            });

            if (response.ok) {
                contentInput.value = '';
                this.loadPosts();
            } else {
                throw new Error('Erro ao criar pensamento');
            }
        } catch (error) {
            this.showError('Erro ao criar pensamento: ' + error.message);
        }
    }

    async searchPosts(query) {
        if (!query.trim()) {
            this.loadPosts();
            return;
        }

        try {
            const response = await fetch(`${this.apiBase}/contentsearch?text=${encodeURIComponent(query)}`);
            this.posts = await response.json();
            this.renderPosts();
        } catch (error) {
            this.showError('Erro ao buscar pensamentos: ' + error.message);
        }
    }

    async handleReaction(postId, emojiType) {
        try {
            const reaction = {
                reaction: emojiType,
                date: new Date().toISOString()
            };

            const response = await fetch(`${this.apiBase}/${postId}/reactions`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(reaction)
            });

            if (response.ok) {
                const updatedPost = await response.json();
                
                // Atualiza o estado local
                const index = this.posts.findIndex(p => p.id === postId);
                if (index !== -1) {
                    this.posts[index] = updatedPost;
                    this.updatePostInDOM(updatedPost);
                }
            } else {
                throw new Error('Erro ao processar reação');
            }
        } catch (error) {
            this.showError('Erro ao reagir: ' + error.message);
        }
    }

    updatePostInDOM(updatedPost) {
        const postElement = document.querySelector(`[data-post-id="${updatedPost.id}"]`);
        if (postElement) {
            const newHTML = this.createPostHTML(updatedPost);
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = newHTML;
            const newNode = tempDiv.firstElementChild;
            postElement.replaceWith(newNode);
            
            // Re-bind events for the new element
            this.bindPostEvents(newNode, updatedPost.id);
        }
    }

    renderPosts() {
        const container = document.getElementById('thoughtsContainer');

        if (this.posts.length === 0) {
            container.innerHTML = '<div class="loading">Nenhum pensamento encontrado. Seja o primeiro a compartilhar!</div>';
            return;
        }

        container.innerHTML = this.posts.map(post => this.createPostHTML(post)).join('');

        // Bind events for all posts
        document.querySelectorAll('.thought-card').forEach(postElement => {
            const postId = postElement.dataset.postId;
            this.bindPostEvents(postElement, postId);
        });
    }

    bindPostEvents(postElement, postId) {
        postElement.querySelectorAll('.emoji-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                this.handleReaction(postId, btn.dataset.emoji);
            });
        });
    }

    createPostHTML(post) {
        const date = new Date(post.date).toLocaleDateString('pt-BR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });

        const reactions = post.reactions || [];
        const reactionsByEmoji = {};
        let currentUserReaction = null;

        reactions.forEach(reaction => {
            if (!reactionsByEmoji[reaction.reaction]) {
                reactionsByEmoji[reaction.reaction] = [];
            }
            reactionsByEmoji[reaction.reaction].push(reaction);

            if (reaction.author && (reaction.author.id === this.currentUser.id || reaction.author.name === this.currentUser.name)) {
                currentUserReaction = reaction.reaction;
            }
        });

        const emojiMap = {
            'like': '👍',
            'love': '❤️',
            'laugh': '😂',
            'think': '🤔',
            'sad': '😞',
            'cry': '😭'
        };

        const reactionsHTML = Object.entries(reactionsByEmoji).map(([reactionCode, reactionList]) => {
            return `
            <div class="reaction-item">
                <span class="reaction-emoji">${emojiMap[reactionCode] || reactionCode}</span>
                <span class="reaction-count">${reactionList.length}</span>
            </div>
        `;
        }).join('');

        return `
            <div class="thought-card" data-post-id="${post.id}">
                <div class="thought-header">
                    <div class="author-info">
                        <div class="author-avatar">${post.author.name.charAt(0).toUpperCase()}</div>
                        <div class="author-details">
                            <div class="author-name">${post.author.name}</div>
                            <div class="thought-date">${date}</div>
                        </div>
                    </div>
                </div>
                <div class="thought-content">${post.body}</div>
                <div class="reactions-section">
                    <div class="reactions-header">
                        <span>Reações</span>
                        <div class="reactions-display">
                            ${reactionsHTML}
                        </div>
                    </div>
                    <div class="add-reaction">
                        <div class="emoji-picker">
                            ${Object.entries(emojiMap).map(([key, emoji]) => `
                                <button class="emoji-btn ${currentUserReaction === key ? 'selected' : ''}" 
                                        data-post="${post.id}" 
                                        data-emoji="${key}">${emoji}</button>
                            `).join('')}
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    showLoading() {
        document.getElementById('thoughtsContainer').innerHTML = '<div class="loading">Carregando...</div>';
    }

    showError(message) {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error';
        errorDiv.textContent = message;
        document.body.appendChild(errorDiv);
        setTimeout(() => errorDiv.remove(), 5000);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new ThoughtsApp();
});